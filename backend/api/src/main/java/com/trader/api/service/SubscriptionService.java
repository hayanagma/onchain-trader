package com.trader.api.service;

import org.springframework.stereotype.Service;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.LedgerClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.identity.subscription.SubscriptionCreateRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionStatusResponse;
import com.trader.shared.enums.PaymentStatus;

import reactor.core.publisher.Mono;

@Service
public class SubscriptionService {

    private final IdentityClient identityClient;
    private final TraderContext traderContext;
    private final LedgerClient ledgerClient;

    public SubscriptionService(IdentityClient identityClient, TraderContext traderContext, LedgerClient ledgerClient) {
        this.identityClient = identityClient;
        this.traderContext = traderContext;
        this.ledgerClient = ledgerClient;
    }

    public Mono<SubscriptionPaymentResponse> subscriptionPayment(SubscriptionPaymentRequest request) {
        Long traderId = traderContext.getCurrentTraderId();

        // Create and return pending payment info
        return ledgerClient.createSubscriptionPayment(traderId, request);
    }

    public Mono<SubscriptionStatusResponse> handleSubscriptionStatus(Long paymentId) {
        Long traderId = traderContext.getCurrentTraderId();

        return ledgerClient.getSubscriptionPaymentStatus(paymentId)
                .flatMap(payment -> {
                    if (payment.getStatus() == PaymentStatus.CONFIRMED) {
                        SubscriptionCreateRequest subscriptionRequest = new SubscriptionCreateRequest(
                                traderId,
                                payment.getPlan(),
                                payment.isAutoRenewal());
                        return identityClient.createSubscription(
                                subscriptionRequest)
                                .thenReturn(new SubscriptionStatusResponse("CONFIRMED"));
                    }
                    return Mono.just(new SubscriptionStatusResponse(payment.getStatus().name()));
                });
    }

    public Mono<SubscriptionResponse> getPlayerSubscription() {
        Long traderId = traderContext.getCurrentTraderId();
        return identityClient.getSubscriptionByTraderId(traderId);
    }
}