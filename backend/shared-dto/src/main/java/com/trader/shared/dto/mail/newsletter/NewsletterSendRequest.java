package com.trader.shared.dto.mail.newsletter;

import jakarta.validation.constraints.NotBlank;

public class NewsletterSendRequest {

    @NotBlank
    private String subject;
    @NotBlank
    private String content;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
