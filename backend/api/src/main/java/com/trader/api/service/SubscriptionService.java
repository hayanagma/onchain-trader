package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.IdentityClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.identity.subscription.SubscriptionRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;

import reactor.core.publisher.Mono;

@Service
public class SubscriptionService {

    private final IdentityClient identityClient;
    private final TraderContext traderContext;

    public SubscriptionService(IdentityClient identityClient, TraderContext traderContext) {
        this.identityClient = identityClient;
        this.traderContext = traderContext;
    }

    public Mono<SubscriptionResponse> subscribe(SubscriptionRequest request) {
        Long traderId = traderContext.getCurrentTraderId();
        return identityClient.subscribeTrader(request, traderId);
    }
}