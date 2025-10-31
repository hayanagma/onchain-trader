package com.trader.ledger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.NetworkAccountService;
import com.trader.shared.dto.ledger.networkaccount.NetworkAccountCreateRequest;

@RestController
@RequestMapping("/api/internal/ledger/network-accounts")
public class NetworkAccountController {

    private final NetworkAccountService networkAccountService;

    public NetworkAccountController(NetworkAccountService networkAccountService) {
        this.networkAccountService = networkAccountService;
    }

    @PostMapping
    public ResponseEntity<Void> createNetworkAccount(@RequestBody NetworkAccountCreateRequest request) {
        networkAccountService.createNetworkAccount(request);
        return ResponseEntity.ok().build();
    }
}