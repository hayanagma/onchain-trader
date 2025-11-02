package com.casino.mail.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.casino.mail.validation.MailValidator;
import com.trader.shared.dto.mail.MailRequest;
import com.trader.shared.dto.mail.newsletter.NewsletterSendRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MailValidator mailValidator;
    private final String fromAddress;
    private final String supportMail;
    private final String contactMail;

    public MailService(
            JavaMailSender mailSender,
            MailValidator mailValidator,
            @Value("${app.mail.from}") String fromAddress,
            @Value("${app.mail.support-to}") String supportMail,
            @Value("${app.mail.contact-to}") String contactMail) {
        this.mailSender = mailSender;
        this.mailValidator = mailValidator;
        this.fromAddress = fromAddress;
        this.supportMail = supportMail;
        this.contactMail = contactMail;
    }

    public void sendSupportMail(MailRequest request) {
        sendMail(supportMail, request);
    }

    public void sendContactMail(MailRequest request) {
        sendMail(contactMail, request);
    }

    public void sendAdmin2Auth(Map<String, String> request) {
        String to = request.get("to");
        String subject = request.get("subject");
        String content = request.get("content");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    public void sendNewsletter(NewsletterSendRequest request, List<String> recipients) {
        for (String email : recipients) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(
                        message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        "UTF-8");
                helper.setFrom(fromAddress);
                helper.setTo(email);
                helper.setSubject(request.getSubject());
                helper.setText(request.getContent(), true);
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to build email", e);
            }
        }
    }

    private void sendMail(String toMail, MailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toMail);
        message.setSubject(request.getSubject());

        StringBuilder body = new StringBuilder();
        if (mailValidator.isValid(request.getFrom())) {
            body.append("From: ").append(request.getFrom()).append("\n\n");
        }
        body.append(request.getMessage());
        message.setText(body.toString());

        mailSender.send(message);
    }

    public void sendNewsletterSubscribed(String email) {
        sendSimpleMail(email, "Subscription confirmed",
                "You’ve successfully subscribed to our newsletter.");
    }

    public void sendNewsletterUnsubscribed(String email) {
        sendSimpleMail(email, "You’ve unsubscribed",
                "You’ve been removed from our newsletter list. You can re-subscribe anytime.");
    }

    private void sendSimpleMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}