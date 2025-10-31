package com.trader.ledger.service;

import org.springframework.stereotype.Service;

import com.trader.ledger.model.NetworkAccount;
import com.trader.ledger.repository.NetworkAccountRepository;
import com.trader.shared.dto.ledger.networkaccount.NetworkAccountCreateRequest;

import jakarta.transaction.Transactional;

@Service
public class NetworkAccountService {

    private final NetworkAccountRepository networkAccountRepository;

    public NetworkAccountService(NetworkAccountRepository networkAccountRepository) {
        this.networkAccountRepository = networkAccountRepository;
    }

    @Transactional
    public void createNetworkAccount(NetworkAccountCreateRequest request) {
        boolean exists = networkAccountRepository.existsByTraderIdAndNetwork(
                request.getTraderId(),
                request.getNetwork());

        if (!exists) {
            NetworkAccount account = new NetworkAccount();
            account.setTraderId(request.getTraderId());
            account.setNetwork(request.getNetwork());
            networkAccountRepository.save(account);
        }
    }

}
