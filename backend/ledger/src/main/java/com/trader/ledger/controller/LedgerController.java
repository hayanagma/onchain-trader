package com.trader.ledger.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.ledger.service.PaymentCurrencyService;
import com.trader.ledger.service.SubscriptionPaymentService;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentStatusResponse;
import com.trader.shared.dto.ledger.paymentcurrency.PaymentCurrencyResponse;

@RestController
@RequestMapping("/api/internal/ledger")
public class LedgerController {

    private final SubscriptionPaymentService subscriptionPaymentService;
    private final PaymentCurrencyService paymentCurrencyService;

    public LedgerController(SubscriptionPaymentService subscriptionPaymentService,
            PaymentCurrencyService paymentCurrencyService) {
        this.subscriptionPaymentService = subscriptionPaymentService;
        this.paymentCurrencyService = paymentCurrencyService;
    }

    @PostMapping("/subscription-payments")
    public ResponseEntity<SubscriptionPaymentResponse> createSubscriptionPayment(
            @RequestParam Long traderId,
            @RequestBody SubscriptionPaymentRequest request) {
        SubscriptionPaymentResponse response = subscriptionPaymentService.createSubscriptionPayment(traderId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment-currencies")
    public ResponseEntity<List<PaymentCurrencyResponse>> getAll() {
        return ResponseEntity.ok(paymentCurrencyService.getAllPaymentCurrencies());
    }

    @GetMapping("/subscription-payments/{id}")
    public ResponseEntity<SubscriptionPaymentStatusResponse> getSubscriptionPaymentStatus(@PathVariable Long id) {
        SubscriptionPaymentStatusResponse response = subscriptionPaymentService.getSubscriptionPaymentStatus(id);
        return ResponseEntity.ok(response);
    }
}