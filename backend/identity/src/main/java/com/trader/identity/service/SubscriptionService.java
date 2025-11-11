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
import com.trader.shared.dto.identity.subscription.SubscriptionCreateRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionResponse;
import com.trader.shared.enums.SubscriptionPlan;

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
        subscription.setStartDate(Instant.now());
        subscription.setEndDate(Instant.now().plus(Duration.ofDays(30)));
        subscription.setActive(true);

        trader.setSubscriptionPlan(request.getPlan());
        traderRepository.save(trader);
        subscriptionRepository.save(subscription);
    }

    public void adminCreateSubscription(SubscriptionCreateRequest request) {
        Trader trader = getTrader(request.getTraderId());

        Subscription subscription = new Subscription();
        subscription.setTrader(trader);
        subscription.setPlan(request.getPlan());
        subscription.setStartDate(Instant.now());
        subscription.setEndDate(null);
        subscription.setActive(true);

        trader.setSubscriptionPlan(request.getPlan());
        traderRepository.save(trader);
        subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long traderId) {
        subscriptionRepository.findByTraderId(traderId).ifPresent(subscription -> {
            subscription.setTrader(null);
            subscriptionRepository.delete(subscription);
        });

        Trader trader = getTrader(traderId);
        trader.setSubscriptionPlan(SubscriptionPlan.FREE);
        traderRepository.save(trader);
    }

    private Trader getTrader(Long traderId) {
        return traderRepository.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Trader not found: " + traderId));
    }

    private Subscription getSubscriptionEntity(Long traderId) {
        return subscriptionRepository.findByTraderId(traderId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found for trader ID: " + traderId));
    }

    public SubscriptionResponse getSubscriptionByTraderId(Long traderId) {
        Subscription subscription = getSubscriptionEntity(traderId);
        return new SubscriptionResponse(
                subscription.getPlan(),
                subscription.isActive(),
                subscription.getStartDate(),
                subscription.getEndDate());
    }

    public SubscriptionResponse getSubscriptionForTrader(Long traderId) {
        return subscriptionRepository.findByTraderId(traderId)
                .map(sub -> new SubscriptionResponse(
                        sub.getPlan(),
                        sub.isActive(),
                        sub.getStartDate(),
                        sub.getEndDate()))
                .orElse(null);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void checkExpirations() {
        Instant now = Instant.now();
        List<Subscription> expired = subscriptionRepository.findAllByActiveTrueAndEndDateBefore(now);
        for (Subscription sub : expired) {
            sub.setActive(false);
            Trader trader = sub.getTrader();
            trader.setSubscriptionPlan(SubscriptionPlan.FREE);
            traderRepository.save(trader);
        }
        subscriptionRepository.saveAll(expired);
    }
}
