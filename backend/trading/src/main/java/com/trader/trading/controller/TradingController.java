package com.trader.trading.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.shared.dto.trading.CreateTradeRequest;
import com.trader.shared.dto.trading.TradeExecutionResponse;
import com.trader.trading.service.TradeService;

@RestController
@RequestMapping("/api/internal/trading")
public class TradingController {

    private final TradeService tradeService;

    public TradingController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/trades")
    public ResponseEntity<List<CreateTradeRequest>> getTradesByTraderId(@RequestParam Long traderId) {
        List<CreateTradeRequest> trades = tradeService.getTradesByTraderId(traderId);
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/executions")
    public List<TradeExecutionResponse> getExecutionsByTrader(@RequestParam Long traderId) {
        return tradeService.getExecutionsByTrader(traderId);
    }
}