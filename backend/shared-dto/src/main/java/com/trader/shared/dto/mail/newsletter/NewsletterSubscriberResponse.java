package com.trader.shared.dto.mail.newsletter;

import java.util.List;


public class NewsletterSubscriberResponse {

    private long count;
    private List<String> emails;

    public NewsletterSubscriberResponse() {
    }

    public NewsletterSubscriberResponse(long count, List<String> emails) {
        this.count = count;
        this.emails = emails;
    }

    public long getCount() {
        return count;
    }

    public List<String> getEmails() {
        return emails;
    }
}