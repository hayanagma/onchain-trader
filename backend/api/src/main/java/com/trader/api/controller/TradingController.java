package com.trader.api.controller;

import com.trader.api.service.TradingService;
import com.trader.shared.dto.trading.CreateTradeRequest;
import com.trader.shared.dto.trading.TradeExecutionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trader/trade")
public class TradingController {

    private final TradingService tradingService;

    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;

    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createTrade(@RequestBody CreateTradeRequest request) {
        tradingService.publishTrade(request);
        return Mono.just(ResponseEntity.accepted().build());
    }

    @GetMapping("/requests")
    public Flux<CreateTradeRequest> getTraderTrades() {
        return tradingService.getTraderTrades();
    }
    
      @GetMapping("/executions")
    public Mono<ResponseEntity<Flux<TradeExecutionResponse>>> getExecutions() {
        return Mono.just(ResponseEntity.ok(tradingService.getTraderExecutions()));
    }
}