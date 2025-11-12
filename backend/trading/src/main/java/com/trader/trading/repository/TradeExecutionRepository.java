package com.trader.trading.repository;

import com.trader.trading.model.TradeExecution;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeExecutionRepository extends JpaRepository<TradeExecution, Long> {
    List<TradeExecution> findByTradeRequest_TraderId(Long traderId);
}