package com.casino.mail.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casino.mail.service.MailService;
import com.trader.shared.dto.mail.MailRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/internal/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> sendContactMail(@Valid @RequestBody MailRequest request) {
        mailService.sendContactMail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/support")
    public ResponseEntity<Void> sendSupportMail(@Valid @RequestBody MailRequest request) {
        mailService.sendSupportMail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-admin-2auth")
    public ResponseEntity<Void> sendAdmin2Auth(@RequestBody Map<String, String> request) {
        mailService.sendAdmin2Auth(request);
        return ResponseEntity.ok().build();
    }

}