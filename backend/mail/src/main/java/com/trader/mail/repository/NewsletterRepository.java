package com.trader.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.mail.model.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}
