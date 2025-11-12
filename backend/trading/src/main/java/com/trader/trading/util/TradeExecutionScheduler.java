package com.trader.trading.util;

import com.trader.shared.enums.TradeStatus;
import com.trader.trading.model.TradeExecution;
import com.trader.trading.model.TradeRequest;
import com.trader.trading.repository.TradeExecutionRepository;
import com.trader.trading.repository.TradeRequestRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class TradeExecutionScheduler {

    private final TradeRequestRepository tradeRequestRepository;
    private final TradeExecutionRepository tradeExecutionRepository;

    public TradeExecutionScheduler(TradeRequestRepository tradeRequestRepository,
            TradeExecutionRepository tradeExecutionRepository) {
        this.tradeRequestRepository = tradeRequestRepository;
        this.tradeExecutionRepository = tradeExecutionRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void simulateExecutions() {
        List<TradeRequest> pending = tradeRequestRepository.findByStatus(TradeStatus.PENDING);
        for (TradeRequest trade : pending) {

            // Mark trade as complete
            trade.setStatus(TradeStatus.COMPLETE);
            trade.setExecutedAt(Instant.now().toEpochMilli());
            trade.setTxHash(UUID.randomUUID().toString());
            tradeRequestRepository.save(trade);

            // Create corresponding execution record
            TradeExecution execution = new TradeExecution();
            execution.setTradeRequest(trade);
            execution.setExecutedAt(Instant.now().toEpochMilli());
            execution.setTxHash(trade.getTxHash());
            execution.setExecutedAmount(trade.getAmountIn());
            execution.setReceivedAmount(trade.getMinAmountOut());
            execution.setProfitLoss(BigDecimal.ZERO);

            tradeExecutionRepository.save(execution);
        }
    }
}