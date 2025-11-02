package com.trader.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.service.SubscriptionService;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionStatusResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trader/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/payment")
    public Mono<SubscriptionPaymentResponse> subscriptionPayment(@RequestBody SubscriptionPaymentRequest request) {
        return subscriptionService.subscriptionPayment(request);
    }

    @GetMapping("/status/{paymentId}")
    public Mono<ResponseEntity<SubscriptionStatusResponse>> handleSubscriptionStatus(@PathVariable Long paymentId) {
        return subscriptionService.handleSubscriptionStatus(paymentId)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Mono<ResponseEntity<SubscriptionResponse>> getPlayerSubscription() {
        return subscriptionService.getPlayerSubscription()
                .map(ResponseEntity::ok);
    }

}