package com.casino.mail.service;

import java.util.List;
import java.util.Optional;

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
import jakarta.validation.Valid;

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
        repository.save(subscriber);
        mailService.sendNewsletterSubscribed(email);
    }

    @Transactional
    public void unsubscribe(@Valid NewsletterSubscribeRequest request) {
        if (!repository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not subscribed");
        }

        repository.deleteByEmail(request.getEmail());
        mailService.sendNewsletterUnsubscribed(request.getEmail());
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