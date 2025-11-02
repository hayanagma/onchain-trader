package com.trader.ledger.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.trader.ledger.model.SubscriptionPayment;
import com.trader.ledger.repository.SubscriptionPaymentRepository;
import com.trader.ledger.validation.SubscriptionPaymentValidator;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentRequest;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentResponse;
import com.trader.shared.dto.identity.subscription.SubscriptionPaymentStatusResponse;
import com.trader.shared.enums.PaymentNetworkType;
import com.trader.shared.enums.PaymentStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SubscriptionPaymentService {

    private final SubscriptionPaymentRepository subscriptionPaymentRepository;
    private final DepositAddressService depositAddressService;
    private final PaymentCurrencyService paymentCurrencyService;
    private final SubscriptionPaymentValidator subscriptionPaymentValidator;

    public SubscriptionPaymentService(SubscriptionPaymentRepository subscriptionPaymentRepository,
            DepositAddressService depositAddressService, PaymentCurrencyService paymentCurrencyService,
            SubscriptionPaymentValidator subscriptionPaymentValidator) {
        this.subscriptionPaymentRepository = subscriptionPaymentRepository;
        this.depositAddressService = depositAddressService;
        this.paymentCurrencyService = paymentCurrencyService;
        this.subscriptionPaymentValidator = subscriptionPaymentValidator;
    }

    @Transactional
    public SubscriptionPaymentResponse createSubscriptionPayment(Long traderId, SubscriptionPaymentRequest request) {
        // validate payment currency

        String code = subscriptionPaymentValidator.normalizeCurrency(request.getPaymentCurrencyCode());
        PaymentNetworkType network = request.getNetwork();

        subscriptionPaymentValidator.validateSubscriptionPayment(code, network);

        paymentCurrencyService.validatePaymentCurrency(code, network);

        SubscriptionPayment payment = new SubscriptionPayment();
        payment.setTraderId(traderId);
        payment.setPlan(request.getPlan());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(BigDecimal.valueOf(1337));
        payment.setPaymentCurrencyCode(code);
        payment.setNetwork(network);

        String depositAddress = depositAddressService.generateAddress(code, traderId);
        payment.setDepositAddress(depositAddress);

        String qrCodeUrl = generateQrUrl(code, depositAddress, payment.getAmount());
        payment.setQrCodeUrl(qrCodeUrl);

        Instant now = Instant.now();
        payment.setCreatedAt(now);
        payment.setExpiresAt(now.plus(Duration.ofHours(3)));

        SubscriptionPayment saved = subscriptionPaymentRepository.save(payment);

        return new SubscriptionPaymentResponse(
                saved.getId(),
                saved.getPaymentCurrencyCode(),
                saved.getDepositAddress(),
                saved.getStatus(),
                saved.getAmount(),
                saved.getQrCodeUrl(),
                saved.getCreatedAt(),
                saved.getExpiresAt());
    }

    public SubscriptionPaymentStatusResponse getSubscriptionPaymentStatus(Long id) {
        SubscriptionPayment payment = subscriptionPaymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubscriptionPayment not found: " + id));

        Instant now = Instant.now();

        if (payment.getStatus() == PaymentStatus.PENDING) {
            if (now.isAfter(payment.getExpiresAt())) {
                payment.setStatus(PaymentStatus.EXPIRED);
            } else {
                // temporary mock confirmation until blockchain integration
                payment.setStatus(PaymentStatus.CONFIRMED);
            }
            subscriptionPaymentRepository.save(payment);
        }

        return new SubscriptionPaymentStatusResponse(
                payment.getId(),
                payment.getStatus(),
                payment.getPlan());
    }

    private String generateQrUrl(String currencyCode, String address, BigDecimal amount) {
        return currencyCode.toLowerCase() + ":" + address + "?amount=" + amount;
    }
}