package com.trader.ledger.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trader.ledger.model.SubscriptionPayment;
import com.trader.shared.enums.PaymentStatus;

@Repository
public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long> {

    List<SubscriptionPayment> findByTraderId(Long traderId);

    Optional<SubscriptionPayment> findByIdAndTraderId(Long id, Long traderId);

    boolean existsByTraderIdAndStatus(Long traderId, PaymentStatus status);
}