package com.trader.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.client.ledger.LedgerClient;
import com.trader.api.service.TraderService;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameResponse;
import com.trader.shared.dto.ledger.paymentcurrency.PaymentCurrencyResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trader")
public class TraderController {

    private final TraderService traderService;
    private final LedgerClient ledgerClient;

    public TraderController(TraderService traderService, LedgerClient ledgerClient) {
        this.traderService = traderService;
        this.ledgerClient = ledgerClient;
    }

    @GetMapping("/profile")
    public Mono<ResponseEntity<TraderProfileResponse>> getTraderProfile() {
        return traderService.getTraderProfile()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/profile/random-username")
    public Mono<ResponseEntity<UsernameResponse>> randomizeUsername() {
        return traderService.randomizeUsername()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/profile/username")
    public Mono<ResponseEntity<UsernameResponse>> getUsername() {
        return traderService.getUsername()
                .map(ResponseEntity::ok);
    }

    @PutMapping("/profile/username")
    public Mono<ResponseEntity<Void>> updateUsername(@RequestBody UpdateUsernameRequest request) {
        return traderService.updateUsername(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/account/delete")
    public Mono<ResponseEntity<Void>> deleteAccount(@RequestBody DeleteAccountRequest request) {
        return traderService.deleteAccount(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @GetMapping("/payment-currencies")
    public Mono<ResponseEntity<List<PaymentCurrencyResponse>>> getAllPaymentCurrencies() {
        return ledgerClient.getAllPaymentCurrencies()
                .collectList()
                .map(ResponseEntity::ok);
    }
}