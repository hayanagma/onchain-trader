package com.trader.ledger.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.trader.ledger.model.Wallet;
import com.trader.ledger.repository.WalletRepository;
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

}
