package com.casino.mail.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.casino.mail.model.NewsletterSubscriber;
import com.casino.mail.repository.NewsletterSubscriberRepository;
import com.casino.mail.validation.MailValidator;
import com.trader.shared.dto.mail.newsletter.NewsletterSendRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscribeRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSubscriberResponse;

import jakarta.transaction.Transactional;

@Service
public class NewsletterService {

    private final NewsletterSubscriberRepository repository;
    private final MailService mailService;
    private final MailValidator mailValidator;

    public NewsletterService(NewsletterSubscriberRepository repository, MailService mailService,
            MailValidator mailValidator) {
        this.repository = repository;
        this.mailService = mailService;
        this.mailValidator = mailValidator;
    }

    public void subscribe(NewsletterSubscribeRequest request) {
        String email = request.getEmail();
        mailValidator.isValid(email);

        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already subscribed");
        }

        NewsletterSubscriber subscriber = new NewsletterSubscriber();
        subscriber.setEmail(email);
        subscriber.setUnsubscribeToken(UUID.randomUUID().toString());
        repository.save(subscriber);

        mailService.sendNewsletterSubscribed(email, subscriber.getUnsubscribeToken());
    }

    @Transactional
    public void unsubscribe(String token) {
        NewsletterSubscriber subscriber = repository.findByUnsubscribeToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid or expired token"));

        repository.delete(subscriber);
        mailService.sendNewsletterUnsubscribed(subscriber.getEmail());
    }

    @Transactional
    public void sendNewsletterToAll(NewsletterSendRequest request) {
        List<String> recipients = repository.findAll()
                .stream()
                .map(NewsletterSubscriber::getEmail)
                .toList();

        mailService.sendNewsletter(request, recipients);
    }

    public Optional<NewsletterSubscriber> findByEmail(String email) {
        return repository.findAll()
                .stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public NewsletterSubscriberResponse getAllSubscribers() {
        List<String> emails = repository.findAll()
                .stream()
                .map(NewsletterSubscriber::getEmail)
                .toList();

        long count = emails.size();

        return new NewsletterSubscriberResponse(count, emails);
    }

}