package com.trader.auth.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

public final class RestTemplateUtil {

    private RestTemplateUtil() {
    }

    public static <T> T get(RestTemplate rt, String url, Class<T> type, Object... uriVars) {
        try {
            return rt.getForObject(url, type, uriVars);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), extractMessage(e), e);
        }
    }

    public static <T> T post(RestTemplate rt, String url, Object request, Class<T> type, Object... uriVars) {
        try {
            return rt.postForObject(url, request, type, uriVars);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), extractMessage(e), e);
        }
    }

    public static <T> ResponseEntity<T> getEntity(RestTemplate rt, String url, Class<T> type, Object... uriVars) {
        try {
            return rt.getForEntity(url, type, uriVars);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), extractMessage(e), e);
        }
    }

    public static <T> ResponseEntity<T> postEntity(RestTemplate rt, String url, Object request, Class<T> type,
            Object... uriVars) {
        try {
            return rt.postForEntity(url, request, type, uriVars);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), extractMessage(e), e);
        }
    }

    private static String extractMessage(HttpClientErrorException e) {
        String body = e.getResponseBodyAsString();
        return (body != null && !body.isBlank()) ? body : e.getStatusText();
    }
}
