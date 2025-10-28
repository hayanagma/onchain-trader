package com.trader.identity.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.identity.model.Trader;
import com.trader.identity.repository.TraderRepository;
import com.trader.identity.validation.TraderValidator;
import com.trader.shared.dto.identity.admin.AdminTraderInternalResponse;
import com.trader.shared.dto.identity.admin.BanRequest;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileInternalResponse;
import com.trader.shared.dto.identity.trader.TraderResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameChangeStatus;
import com.trader.shared.dto.identity.trader.UsernameResponse;

@Service
public class TraderService {

    private final TraderRepository traderRepository;
    private final RandomNameGenerator randomNameGenerator;
    private final TraderValidator traderValidator;

    public TraderService(TraderRepository traderRepository,
            RandomNameGenerator randomNameGenerator,
            TraderValidator traderValidator) {
        this.traderRepository = traderRepository;
        this.randomNameGenerator = randomNameGenerator;
        this.traderValidator = traderValidator;
    }

    public TraderResponse getTraderById(Long traderId) {
        Trader trader = traderRepository.findById(traderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trader not found: " + traderId));
        return toResponse(trader);
    }

    public TraderResponse createTrader() {
        Trader trader = new Trader();
        trader.setUsername(randomNameGenerator.generate());
        trader.setBanned(false);
        trader.setTokenVersion(1);
        trader.setSubscribed(false);
        Trader saved = traderRepository.save(trader);
        return toResponse(saved);
    }

    private TraderResponse toResponse(Trader trader) {
        return new TraderResponse(
                trader.getId(),
                trader.getUsername(),
                trader.isBanned(),
                trader.getBannedReason(),
                trader.getTokenVersion(),
                trader.isSubscribed());
    }

    public int getTokenVersion(Long traderId) {
        return getTraderById(traderId).getTokenVersion();
    }

    public void bumpTokenVersion(Long traderId) {
        traderRepository.bumpTokenVersionById(traderId);
    }

    public TraderProfileInternalResponse getTraderProfile(Long traderId) {
        Trader trader = getTraderEntity(traderId);
        UsernameChangeStatus status = traderValidator.getUsernameChangeStatus(trader);
        return new TraderProfileInternalResponse(
                trader.getId(),
                trader.getUsername(),
                status,
                trader.isSubscribed());
    }

    public Trader getTraderEntity(Long traderId) {
        return traderRepository.findById(traderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Trader not found: " + traderId));
    }

    public void setBanStatus(BanRequest request) {
        Trader trader = getTraderEntity(request.getTraderId());
        trader.setBanned(request.isBanned());
        trader.setBannedReason(request.isBanned() ? request.getReason() : null);
        trader.setTokenVersion(trader.getTokenVersion() + 1);
        traderRepository.save(trader);
    }

    public List<AdminTraderInternalResponse> getTraders() {
        return traderRepository.findAll()
                .stream()
                .map(this::toAdminTraderInternalResponse)
                .toList();
    }

    public AdminTraderInternalResponse getAdminTraderById(Long traderId) {
        Trader trader = getTraderEntity(traderId);
        return toAdminTraderInternalResponse(trader);
    }

    private AdminTraderInternalResponse toAdminTraderInternalResponse(Trader trader) {
        return new AdminTraderInternalResponse(
                trader.getId(),
                trader.getUsername(),
                trader.isBanned(),
                trader.getBannedReason(),
                trader.isActive());
    }

    public UsernameResponse randomizeUsername(Long traderId) {
        String username = randomNameGenerator.generate();
        return new UsernameResponse(username);
    }

    public void updateUsername(Long traderId, UpdateUsernameRequest request) {
        Trader trader = getTraderEntity(traderId);
        UsernameChangeStatus status = traderValidator.getUsernameChangeStatus(trader);
        if (!status.isCanChange()) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "You can only change your username once every 7 days");
        }
        trader.setUsername(request.getUsername());
        trader.setLastUsernameChangeAt(Instant.now());
        traderRepository.save(trader);
    }

    public UsernameResponse getUsername(Long traderId) {
        Trader trader = getTraderEntity(traderId);
        return new UsernameResponse(trader.getUsername());
    }

    public void deleteTraderAccount(Long traderId, DeleteAccountRequest request) {
        if (!"I agree".equals(request.getConfirmation())) {
            throw new IllegalArgumentException("Invalid confirmation text");
        }
        Trader trader = getTraderEntity(traderId);
        trader.setActive(false);
        traderRepository.save(trader);
        bumpTokenVersion(traderId);
    }
}