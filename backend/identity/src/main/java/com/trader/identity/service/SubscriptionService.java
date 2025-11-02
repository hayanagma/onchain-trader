package com.trader.identity.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.trader.identity.model.Subscription;
import com.trader.identity.model.Trader;
import com.trader.identity.repository.SubscriptionRepository;
import com.trader.identity.repository.TraderRepository;
import com.trader.shared.dto.identity.subscription.SubscriptionCreateRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final TraderRepository traderRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, TraderRepository traderRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.traderRepository = traderRepository;
    }

    public void createSubscription(SubscriptionCreateRequest request) {
        Trader trader = getTrader(request.getTraderId());

        Subscription subscription = new Subscription();
        subscription.setTrader(trader);
        subscription.setPlan(request.getPlan());
        subscription.setAutoRenewal(request.isAutoRenewal());
        subscription.setStartDate(Instant.now());
        subscription.setEndDate(Instant.now().plus(Duration.ofDays(30)));
        subscription.setActive(true);

        subscriptionRepository.save(subscription);
    }

    private Trader getTrader(Long traderId) {
        return traderRepository.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found: " + traderId));
    }

    public SubscriptionResponse getSubscriptionByTraderId(Long traderId) {
        Subscription subscription = subscriptionRepository.findByTraderId(traderId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found for trader: " + traderId));

        return new SubscriptionResponse(
                subscription.getPlan(),
                subscription.isActive(),
                subscription.isAutoRenewal(),
                subscription.getStartDate(),
                subscription.getEndDate());
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

    /*
     * @Scheduled(cron = "0 0 * * * *")
     * public void checkExpirations() {
     * Instant now = Instant.now();
     * List<Subscription> expired =
     * subscriptionRepository.findAllByActiveTrueAndEndDateBefore(now);
     * for (Subscription sub : expired) {
     * sub.setActive(false);
     * }
     * subscriptionRepository.saveAll(expired);
     * }
     * 
     * @Scheduled(cron = "0 0 * * * *")
     * public void renewSubscriptions() {
     * Instant now = Instant.now();
     * List<Subscription> renewables =
     * subscriptionRepository.findAllByAutoRenewalTrueAndEndDateBefore(now);
     * for (Subscription sub : renewables) {
     * sub.setStartDate(now);
     * sub.setEndDate(now.plus(Duration.ofDays(sub.getRenewalPeriodDays())));
     * sub.setActive(true);
     * }
     * subscriptionRepository.saveAll(renewables);
     * }
     */
}
