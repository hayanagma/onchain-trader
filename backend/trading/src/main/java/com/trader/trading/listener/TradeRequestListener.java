package com.trader.trading.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.trader.shared.dto.trading.CreateTradeRequest;
import com.trader.trading.service.TradeService;

import static com.trader.trading.config.RabbitConfig.TRADE_REQUEST_QUEUE;

@Component
public class TradeRequestListener {

    private final TradeService tradeService;

    public TradeRequestListener(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RabbitListener(queues = TRADE_REQUEST_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void handleTradeRequest(CreateTradeRequest request) {
        tradeService.handleNewTrade(request);
    }
}