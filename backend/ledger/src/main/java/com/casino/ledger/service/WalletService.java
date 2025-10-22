package com.casino.ledger.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.casino.ledger.dto.WalletResponse;
import com.casino.ledger.model.Wallet;
import com.casino.ledger.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final BalanceService balanceService;

    public WalletService(WalletRepository walletRepository, BalanceService balanceService) {
        this.walletRepository = walletRepository;
        this.balanceService = balanceService;

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
        balanceService.createBalancesForWallet(saved, network);

        return saved;
    }

}
