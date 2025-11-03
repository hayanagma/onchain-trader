package com.trader.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.client.IdentityClient;
import com.trader.api.client.ledger.CurrencyClient;
import com.trader.api.client.ledger.LedgerClient;
import com.trader.api.client.mail.NewsletterClient;
import com.trader.api.client.mail.UpdateClient;
import com.trader.api.service.TraderService;
import com.trader.shared.dto.identity.admin.AdminTraderResponse;
import com.trader.shared.dto.identity.admin.BanRequest;
import com.trader.shared.dto.ledger.currency.CurrencyResponse;
import com.trader.shared.dto.ledger.paymentcurrency.PaymentCurrencyResponse;
import com.trader.shared.dto.mail.newsletter.NewsletterSendRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscriberResponse;
import com.trader.shared.dto.mail.update.UpdateCreate;
import com.trader.shared.dto.mail.update.UpdateEditRequest;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TraderService traderService;
    private final IdentityClient identityClient;
    private final CurrencyClient currencyClient;
    private final LedgerClient ledgerClient;
    private final NewsletterClient newsletterClient;
    private final UpdateClient updateClient;

    public AdminController(
            TraderService traderService,
            IdentityClient identityClient,
            CurrencyClient currencyClient, LedgerClient ledgerClient, NewsletterClient newsletterClient,
            UpdateClient updateClient) {
        this.traderService = traderService;
        this.identityClient = identityClient;
        this.currencyClient = currencyClient;
        this.ledgerClient = ledgerClient;
        this.newsletterClient = newsletterClient;
        this.updateClient = updateClient;
    }

    @GetMapping("/traders")
    public Mono<ResponseEntity<List<AdminTraderResponse>>> getTraders(
            @RequestParam(required = false) String walletAddress) {
        return traderService.getTraders(walletAddress)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PutMapping("/trader/ban-status")
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

    @GetMapping("/payment-currencies")
    public Mono<ResponseEntity<List<PaymentCurrencyResponse>>> getAllPaymentCurrencies() {
        return ledgerClient.getAllPaymentCurrencies()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/newsletters/subscribers")
    public Mono<ResponseEntity<List<NewsletterSubscriberResponse>>> getAllSubscribers() {
        return newsletterClient.getAllSubscribers()
                .map(ResponseEntity::ok);
    }

    @PostMapping("/newsletters/send")
    public Mono<ResponseEntity<Void>> sendNewsletter(@RequestBody NewsletterSendRequest request) {
        return newsletterClient.sendNewsletter(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/updates/create")
    public Mono<ResponseEntity<Void>> createUpdate(@RequestBody UpdateCreate request) {
        return updateClient.createUpdate(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PutMapping("/updates/edit")
    public Mono<ResponseEntity<Void>> updateUpdate(@RequestBody UpdateEditRequest request) {
        return updateClient.editUpdate(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }
}