package com.trader.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.service.SubscriptionService;
import com.trader.shared.dto.identity.subscription.SubscriptionRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trader/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public Mono<SubscriptionResponse> subscribe(@RequestBody SubscriptionRequest request) {
        return subscriptionService.subscribe(request);
    }
}