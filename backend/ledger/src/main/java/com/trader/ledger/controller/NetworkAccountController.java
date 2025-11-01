package com.trader.ledger.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.NetworkAccountService;
import com.trader.shared.dto.ledger.networkaccount.NetworkAccountResponse;

@RestController
@RequestMapping("/api/internal/ledger/network-accounts")
public class NetworkAccountController {

    private final NetworkAccountService networkAccountService;

    public NetworkAccountController(NetworkAccountService networkAccountService) {
        this.networkAccountService = networkAccountService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<Void> initializeNetworkAccounts(@RequestParam Long traderId) {
        networkAccountService.initializeNetworkAccounts(traderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-trader")
    public ResponseEntity<List<NetworkAccountResponse>> getNetworkAccountsByTrader(@RequestParam Long traderId) {
        List<NetworkAccountResponse> accounts = networkAccountService.getNetworkAccountsByTrader(traderId);
        return ResponseEntity.ok(accounts);
    }

}