package com.casino.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casino.mail.model.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}
