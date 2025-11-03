package com.casino.mail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casino.mail.model.NewsletterSubscriber;

@Repository
public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long> {
    boolean existsByEmail(String email);

    Optional<NewsletterSubscriber> findByUnsubscribeToken(String unsubscribeToken);

}