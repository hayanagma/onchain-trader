package com.trader.shared.dto.mail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MailRequest {

    @NotBlank
    private String subject;
    
    @NotBlank
    private String message;

    @Email
    private String from;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}