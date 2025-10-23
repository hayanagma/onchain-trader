package com.trader.ledger.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.ledger.model.Wallet;
import com.trader.ledger.repository.WalletRepository;
import com.trader.shared.dto.ledger.wallet.WalletPlayerResponse;
import com.trader.shared.dto.ledger.wallet.WalletResponse;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<WalletResponse> findByAddressAndNetwork(String address, String network) {
        return walletRepository.findByAddressAndNetwork(address, network)
                .map(wallet -> new WalletResponse(
                        wallet.getId(),
                        wallet.getAddress(),
                        wallet.getNetwork(),
                        wallet.getPlayerId()));
    }

    public void ensureWallet(Long playerId, String address, String network) {
        walletRepository.findByAddressAndNetwork(address, network)
                .orElseGet(() -> createWallet(playerId, address, network));
    }

    private Wallet createWallet(Long playerId, String address, String network) {
        Wallet wallet = new Wallet();
        wallet.setPlayerId(playerId);
        wallet.setAddress(address);
        wallet.setNetwork(network);

        Wallet saved = walletRepository.save(wallet);
        return saved;
    }

    public WalletPlayerResponse getWalletWithBalancesByPlayerId(Long playerId) {
        Wallet wallet = getWalletForPlayerEntity(playerId);
        return new WalletPlayerResponse(wallet.getAddress(), wallet.getNetwork());
    }

    public Long findPlayerIdByWalletAddress(String address) {
        Wallet wallet = walletRepository.findByAddress(address)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Wallet not found for address " + address));
        return wallet.getPlayerId();
    }

    public void cleanupPlayerWallet(Long playerId) {
        Wallet wallet = getWalletForPlayerEntity(playerId);
        wallet.setAddress("unlinked-" + wallet.getId());
        walletRepository.save(wallet);
    }
    
    public Wallet getWalletForPlayerEntity(Long playerId) {
        return walletRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Wallet not found for playerId " + playerId));
    }

}
