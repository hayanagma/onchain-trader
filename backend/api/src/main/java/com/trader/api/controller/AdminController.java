package com.trader.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.service.PlayerService;
import com.trader.shared.dto.identity.admin.AdminPlayerResponse;
import com.trader.shared.dto.identity.admin.BanRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PlayerService playerService;
    private final IdentityClient identityClient;
    private final CurrencyClient currencyClient;

    public AdminController(
            PlayerService playerService, IdentityClient identityClient,
            CurrencyClient currencyClient) {
        this.playerService = playerService;
        this.identityClient = identityClient;
        this.currencyClient = currencyClient;
    }
 
    @GetMapping("/players")
    public Mono<ResponseEntity<List<AdminPlayerResponse>>> getPlayers(
            @RequestParam(required = false) String walletAddress) {
        return playerService.getPlayers(walletAddress)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PutMapping("/player/ban-status")
    public Mono<ResponseEntity<Void>> updateBanStatus(@RequestBody BanRequest request) {
        return identityClient.updateBanStatus(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
 
    @GetMapping("/currencies")
    public Mono<ResponseEntity<List<CurrencyResponse>>> getAllCurrencies() {
        return currencyClient.getAllCurrencies()
                .collectList()
                .map(ResponseEntity::ok);
    }
}
 