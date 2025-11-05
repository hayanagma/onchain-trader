package com.trader.auth.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.trader.auth.util.RestTemplateUtil;

@Component
public class MailClient {

    private final RestTemplate restTemplate;

    public MailClient(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .rootUri("http://mail:8086/api/internal/mail")
                .build();
    }

    public void sendAdmin2AuthMail(String to, String code) {
        Map<String, String> body = new HashMap<>();
        body.put("to", to);
        body.put("subject", "Admin 2FA Verification");
        body.put("content", "Your verification code is: " + code);

        RestTemplateUtil.post(restTemplate, "/send-admin-2auth", body, Void.class);
    }
}