package com.trader.identity.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trader.identity.model.Subscription;
import com.trader.identity.model.Trader;
import com.trader.identity.repository.SubscriptionRepository;
import com.trader.identity.repository.TraderRepository;
import com.trader.shared.dto.identity.subscription.SubscriptionRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.enums.SubscriptionPlan;
import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final TraderRepository traderRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, TraderRepository traderRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.traderRepository = traderRepository;
    }

    @Transactional
    public SubscriptionResponse subscribe(Long traderId, SubscriptionRequest request) {
        Trader trader = getTrader(traderId);
        Subscription subscription = getOrCreateSubscription(trader);

        activateSubscription(subscription, request.getPlan(), request.isAutoRenewal());
        updateTraderSubscribed(trader,request.getPlan());

        Subscription saved = subscriptionRepository.save(subscription);

        return new SubscriptionResponse(
                saved.getPlan(),
                saved.isActive(),
                saved.isAutoRenewal(),
                saved.getStartDate(),
                saved.getEndDate());
    }

    private Trader getTrader(Long traderId) {
        return traderRepository.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found: " + traderId));
    }

    private Subscription getOrCreateSubscription(Trader trader) {
        return subscriptionRepository.findByTraderId(trader.getId())
                .orElseGet(() -> {
                    Subscription newSub = new Subscription();
                    newSub.setTrader(trader);
                    newSub.setRenewalPeriodDays(30);
                    return newSub;
                });
    }

    private void activateSubscription(Subscription subscription, SubscriptionPlan plan, boolean autoRenewal) {
        Instant now = Instant.now();
        subscription.setPlan(plan);
        subscription.setStartDate(now);
        subscription.setEndDate(now.plus(Duration.ofDays(subscription.getRenewalPeriodDays())));
        subscription.setAutoRenewal(autoRenewal);
        subscription.setActive(true);
    }

    private void updateTraderSubscribed(Trader trader, SubscriptionPlan subscriptionPlan) {
        trader.setSubscriptionPlan(subscriptionPlan);
        traderRepository.save(trader);
    }

    public SubscriptionResponse getSubscriptionForTrader(Long traderId) {
        return subscriptionRepository.findByTraderId(traderId)
                .map(subscription -> new SubscriptionResponse(
                        subscription.getPlan(),
                        subscription.isActive(),
                        subscription.isAutoRenewal(),
                        subscription.getStartDate(),
                        subscription.getEndDate()))
                .orElse(null);
    }
    
    @Scheduled(cron = "0 0 * * * *")
    public void checkExpirations() {
        Instant now = Instant.now();
        List<Subscription> expired = subscriptionRepository.findAllByActiveTrueAndEndDateBefore(now);
        for (Subscription sub : expired) {
            sub.setActive(false);
        }
        subscriptionRepository.saveAll(expired);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void renewSubscriptions() {
        Instant now = Instant.now();
        List<Subscription> renewables = subscriptionRepository.findAllByAutoRenewalTrueAndEndDateBefore(now);
        for (Subscription sub : renewables) {
            sub.setStartDate(now);
            sub.setEndDate(now.plus(Duration.ofDays(sub.getRenewalPeriodDays())));
            sub.setActive(true);
        }
        subscriptionRepository.saveAll(renewables);
    }

}
