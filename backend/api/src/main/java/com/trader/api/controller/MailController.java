package com.trader.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trader.api.client.mail.MailClient;
import com.trader.api.client.mail.NewsletterClient;
import com.trader.api.client.mail.UpdateClient;
import com.trader.shared.dto.mail.MailRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscribeRequest;
import com.trader.shared.dto.mail.update.UpdateResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/public")
public class MailController {

    private final MailClient mailClient;
    private final NewsletterClient newsletterClient;
    private final UpdateClient updateClient;

    public MailController(MailClient mailClient,
            NewsletterClient newsletterClient, UpdateClient updateClient) {
        this.mailClient = mailClient;
        this.newsletterClient = newsletterClient;
        this.updateClient = updateClient;
    }

    @PostMapping("/mail/contact")
    public Mono<ResponseEntity<Void>> sendContactMail(@Valid @RequestBody MailRequest request) {
        return mailClient.sendContactMail(request).then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/mail/support")
    public Mono<ResponseEntity<Void>> sendSupportMail(@Valid @RequestBody MailRequest request) {
        return mailClient.sendSupportMail(request).then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/newsletter/subscribe")
    public Mono<ResponseEntity<Void>> subscribe(@RequestBody NewsletterSubscribeRequest request) {
        return newsletterClient.subscribe(request)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @PostMapping("/newsletter/unsubscribe")
    public Mono<ResponseEntity<Void>> unsubscribe(@RequestParam String token) {
        return newsletterClient.unsubscribe(token)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @GetMapping("/updates")
    public Flux<UpdateResponse> getAllUpdates() {
        return updateClient.getAllUpdates();
    }
}
