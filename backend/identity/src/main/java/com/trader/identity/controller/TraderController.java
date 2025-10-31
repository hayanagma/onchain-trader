package com.trader.identity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.identity.service.SubscriptionService;
import com.trader.identity.service.TraderService;
import com.trader.shared.dto.identity.subscription.SubscriptionRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.identity.trader.DeleteAccountRequest;
import com.trader.shared.dto.identity.trader.TraderProfileInternalResponse;
import com.trader.shared.dto.identity.trader.TraderResponse;
import com.trader.shared.dto.identity.trader.UpdateUsernameRequest;
import com.trader.shared.dto.identity.trader.UsernameResponse;

@RestController
@RequestMapping("/api/internal/identity/traders")
public class TraderController {

    private final TraderService traderService;
    private final SubscriptionService subscriptionService;

    public TraderController(TraderService traderService, SubscriptionService subscriptionService) {
        this.traderService = traderService;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/create")
    public ResponseEntity<TraderResponse> createTrader() {
        return ResponseEntity.ok(traderService.createTrader());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraderResponse> getTrader(@PathVariable Long id) {
        return ResponseEntity.ok(traderService.getTraderById(id));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<TraderProfileInternalResponse> getTraderProfile(@PathVariable Long id) {
        return ResponseEntity.ok(traderService.getTraderProfile(id));
    }

    @PostMapping("/{id}/profile/random-username")
    public ResponseEntity<UsernameResponse> randomizeUsername(@PathVariable Long id) {
        return ResponseEntity.ok(traderService.randomizeUsername(id));
    }

    @GetMapping("/{id}/profile/username")
    public ResponseEntity<UsernameResponse> getUsername(@PathVariable Long id) {
        return ResponseEntity.ok(traderService.getUsername(id));
    }

    @PutMapping("/{id}/profile/username")
    public ResponseEntity<Void> updateUsername(@PathVariable Long id,
            @RequestBody UpdateUsernameRequest request) {
        traderService.updateUsername(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTraderAccount(@PathVariable Long id,
            @RequestBody DeleteAccountRequest request) {
        traderService.deleteTraderAccount(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @PathVariable Long id,
            @RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.subscribe(id, request));
    }
}