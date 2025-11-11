package com.trader.trading.repository;

import com.trader.trading.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserAddress(String userAddress);
    boolean existsByNonceAndUserAddress(Long nonce, String userAddress);
}
