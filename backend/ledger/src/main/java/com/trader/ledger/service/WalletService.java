package com.trader.ledger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.Wallet;
import com.trader.ledger.repository.WalletRepository;
import com.trader.shared.dto.ledger.wallet.WalletResponse;
import com.trader.shared.dto.ledger.wallet.WalletTraderResponse;
import com.trader.shared.enums.NetworkType;

import jakarta.transaction.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TraderCurrencyService traderCurrencyService;

    public WalletService(WalletRepository walletRepository, TraderCurrencyService traderCurrencyService) {
        this.walletRepository = walletRepository;
        this.traderCurrencyService = traderCurrencyService;
    }

    public Optional<WalletResponse> findByAddressAndNetwork(String address, NetworkType network) {
        return walletRepository.findByAddressAndNetwork(address, network)
                .map(wallet -> new WalletResponse(
                        wallet.getId(),
                        wallet.getAddress(),
                        wallet.getNetwork(),
                        wallet.getTraderId()));
    }

    public void ensureWallet(Long traderId, String address, NetworkType network) {
        walletRepository.findByAddressAndNetwork(address, network)
                .orElseGet(() -> createWallet(traderId, address, network));
    }

    public List<WalletTraderResponse> getWalletsByTraderId(Long traderId) {
        return walletRepository.findAllByTraderId(traderId).stream()
                .map(wallet -> new WalletTraderResponse(
                        wallet.getId(),
                        wallet.getAddress(),
                        wallet.getNetwork()))
                .toList();
    }

    public Wallet createWallet(Long traderId, String address, NetworkType network) {
        ensureSameNetwork(traderId, network);

        Wallet wallet = new Wallet();
        wallet.setTraderId(traderId);
        wallet.setAddress(address);
        wallet.setNetwork(network);
        return walletRepository.save(wallet);
    }

    public void ensureSameNetwork(Long traderId, NetworkType network) {
        List<Wallet> existingWallets = walletRepository.findAllByTraderId(traderId);
        boolean hasDifferentNetwork = existingWallets.stream()
                .anyMatch(w -> !w.getNetwork().equals(network));

        if (hasDifferentNetwork) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Trader already has a wallet on a different network");
        }
    }

    public WalletTraderResponse getWalletByTraderId(Long traderId) {
        Wallet wallet = getWalletForTraderEntity(traderId);
        return new WalletTraderResponse(wallet.getId(), wallet.getAddress(), wallet.getNetwork());
    }

    public Long findTraderIdByWalletAddress(String address) {
        Wallet wallet = walletRepository.findByAddress(address)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Wallet not found for address " + address));
        return wallet.getTraderId();
    }

    @Transactional
    public void cleanupTraderWallet(Long traderId) {
        List<Wallet> wallets = walletRepository.findAllByTraderId(traderId);

        traderCurrencyService.removeAllForTrader(traderId);
        if (wallets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No wallets found for trader");
        }

        for (Wallet wallet : wallets) {
            wallet.setAddress("unlinked-" + wallet.getId());
        }

        walletRepository.saveAll(wallets);
    }

    @Transactional
    public void removeTraderWallet(Long traderId, Long walletId) {
        Wallet wallet = walletRepository.findByIdAndTraderId(walletId, traderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found"));

        wallet.setAddress("unlinked-" + wallet.getId());
        walletRepository.save(wallet);
    }

    public Wallet getWalletForTraderEntity(Long traderId) {
        return walletRepository.findByTraderId(traderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Wallet not found for traderId " + traderId));
    }

    public NetworkType getTraderNetwork(Long traderId) {
        return getWalletsByTraderId(traderId).stream()
                .findFirst()
                .map(WalletTraderResponse::getNetwork)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trader has no wallet"));
    }


}