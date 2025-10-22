package com.casino.identity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.casino.identity.validation.TokenValidator;

@RestController
@RequestMapping("/api/internal/identity")
public class IdentityController {

    private final TokenValidator tokenValidator;

    public IdentityController(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @GetMapping("/token-version")
    public ResponseEntity<Integer> getTokenVersion(
            @RequestParam String role,
            @RequestParam String subject) {
        return ResponseEntity.ok(tokenValidator.getTokenVersion(role, subject));
    }

    @PostMapping("/bump-version")
    public ResponseEntity<Void> bumpTokenVersion(
            @RequestParam String role,
            @RequestParam String subject) {
        tokenValidator.bumpTokenVersion(role, subject);
        return ResponseEntity.ok().build();
    }
}
