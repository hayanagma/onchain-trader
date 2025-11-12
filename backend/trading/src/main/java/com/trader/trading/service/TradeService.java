package com.trader.trading.service;

import com.trader.shared.dto.trading.CreateTradeRequest;
import com.trader.shared.dto.trading.TradeExecutionResponse;
import com.trader.shared.enums.TradeStatus;
import com.trader.trading.model.TradeRequest;
import com.trader.trading.repository.TradeExecutionRepository;
import com.trader.trading.repository.TradeRequestRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class TradeService {

    private final TradeRequestRepository tradeRequestRepository;
    private final TradeExecutionRepository tradeExecutionRepository;

    public TradeService(TradeRequestRepository tradeRequestRepository, TradeExecutionRepository tradeExecutionRepository) {
        this.tradeRequestRepository = tradeRequestRepository;
        this.tradeExecutionRepository = tradeExecutionRepository;
    }

    public void handleNewTrade(CreateTradeRequest request) {
        TradeRequest trade = new TradeRequest();
        trade.setTraderId(request.getTraderId());
        trade.setWalletAddress(request.getWalletAddress());
        trade.setTokenIn(request.getTokenIn());
        trade.setTokenOut(request.getTokenOut());
        trade.setAmountIn(request.getAmountIn());
        trade.setMinAmountOut(request.getMinAmountOut());
        trade.setStatus(TradeStatus.PENDING);
        trade.setCreatedAt(Instant.now().toEpochMilli());

        tradeRequestRepository.save(trade);
    }

    public void markComplete(TradeRequest trade, String txHash) {
        trade.setStatus(TradeStatus.COMPLETE);
        trade.setTxHash(txHash);
        trade.setExecutedAt(Instant.now().toEpochMilli());
        tradeRequestRepository.save(trade);
    }

    public List<CreateTradeRequest> getTradesByTraderId(Long traderId) {
        return tradeRequestRepository.findByTraderId(traderId)
                .stream()
                .map(trade -> {
                    CreateTradeRequest dto = new CreateTradeRequest();
                    dto.setTraderId(trade.getTraderId());
                    dto.setWalletAddress(trade.getWalletAddress());
                    dto.setTokenIn(trade.getTokenIn());
                    dto.setTokenOut(trade.getTokenOut());
                    dto.setAmountIn(trade.getAmountIn());
                    dto.setMinAmountOut(trade.getMinAmountOut());
                    return dto;
                })
                .toList();
    }

    
    public List<TradeExecutionResponse> getExecutionsByTrader(Long traderId) {
        return tradeExecutionRepository.findByTradeRequest_TraderId(traderId)
                .stream()
                .map(exec -> new TradeExecutionResponse(
                        exec.getId(),
                        exec.getTradeRequest().getId(),
                        exec.getExecutedAt(),
                        exec.getTxHash(),
                        exec.getExecutedAmount(),
                        exec.getReceivedAmount(),
                        exec.getProfitLoss()
                ))
                .toList();
    }
}