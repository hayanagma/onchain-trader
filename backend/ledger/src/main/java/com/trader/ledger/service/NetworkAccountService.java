package com.trader.ledger.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.trader.ledger.model.NetworkAccount;
import com.trader.ledger.repository.NetworkAccountRepository;
import com.trader.shared.dto.ledger.networkaccount.NetworkAccountResponse;
import com.trader.shared.enums.NetworkType;

import jakarta.transaction.Transactional;

@Service
public class NetworkAccountService {

    private final NetworkAccountRepository networkAccountRepository;

    public NetworkAccountService(NetworkAccountRepository networkAccountRepository) {
        this.networkAccountRepository = networkAccountRepository;
    }

    @Transactional
    public void initializeNetworkAccounts(Long traderId) {
        for (NetworkType network : NetworkType.values()) {
            if (!networkAccountRepository.existsByTraderIdAndNetwork(traderId, network)) {
                NetworkAccount account = new NetworkAccount();
                account.setTraderId(traderId);
                account.setNetwork(network);
                networkAccountRepository.save(account);
            }
        }
    }

    public List<NetworkAccountResponse> getNetworkAccountsByTrader(Long traderId) {
        return networkAccountRepository.findByTraderId(traderId)
                .stream()
                .map(account -> new NetworkAccountResponse(
                        account.getId(),
                        account.getTraderId(),
                        account.getNetwork()))
                .toList();
    }

}
