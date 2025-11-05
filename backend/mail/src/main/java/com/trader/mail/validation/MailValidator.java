package com.trader.mail.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

@Component
public class MailValidator {

    private final Pattern emailPattern = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public boolean isValid(String email) {
        if (email == null || email.isBlank() || !emailPattern.matcher(email).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }
        return true;
    }
}
