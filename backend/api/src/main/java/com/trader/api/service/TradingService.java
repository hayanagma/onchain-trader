package com.trader.api.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.trader.api.client.TradingClient;
import com.trader.api.security.TraderContext;
import com.trader.shared.dto.trading.CreateTradeRequest;
import com.trader.shared.dto.trading.TradeExecutionResponse;

import reactor.core.publisher.Flux;

import static com.trader.api.config.RabbitConfig.TRADE_REQUEST_QUEUE;

@Service
public class TradingService {

    private final RabbitTemplate rabbitTemplate;
    private final TradingClient tradingClient;
    private final TraderContext traderContext;

    public TradingService(RabbitTemplate rabbitTemplate, TraderContext traderContext, TradingClient tradingClient) {
        this.rabbitTemplate = rabbitTemplate;
        this.tradingClient = tradingClient;
        this.traderContext = traderContext;
    }

    public void publishTrade(CreateTradeRequest request) {
        rabbitTemplate.convertAndSend(TRADE_REQUEST_QUEUE, request);
    }

    public Flux<CreateTradeRequest> getTraderTrades() {
        Long traderId = traderContext.getCurrentTraderId();
        return tradingClient.getTradesByTraderId(traderId);
    }

    public Flux<TradeExecutionResponse> getTraderExecutions() {
        Long traderId = traderContext.getCurrentTraderId();
        return tradingClient.getExecutionsByTraderId(traderId);
    }

}