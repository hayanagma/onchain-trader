package com.casino.mail.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casino.mail.service.NewsletterService;
import com.trader.shared.dto.mail.newsletter.NewsletterResponse;
import com.trader.shared.dto.mail.newsletter.NewsletterSendRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscribeRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscriberResponse;

@RestController
@RequestMapping("/api/internal/newsletter")
public class NewsletterController {

    private final NewsletterService newsletterService;

    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody NewsletterSubscribeRequest request) {
        newsletterService.subscribe(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestParam String token) {
        newsletterService.unsubscribe(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subscribers")
    public ResponseEntity<NewsletterSubscriberResponse> getAllSubscribers() {
        return ResponseEntity.ok(newsletterService.getAllSubscribers());
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNewsletter(@RequestBody NewsletterSendRequest request) {
        newsletterService.sendNewsletterToAll(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<NewsletterResponse>> getAllNewsletters() {
        return ResponseEntity.ok(newsletterService.getAllNewsletters());
    }
}