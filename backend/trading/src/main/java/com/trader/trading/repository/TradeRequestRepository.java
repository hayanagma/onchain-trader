package com.trader.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.trader.shared.enums.TradeStatus;
import com.trader.trading.model.TradeRequest;

public interface TradeRequestRepository extends JpaRepository<TradeRequest, Long> {

    List<TradeRequest> findByStatus(TradeStatus status);

    List<TradeRequest> findByTraderId(Long traderId);
}