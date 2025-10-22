package com.trader.identity.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.identity.service.AdminService;
import com.trader.identity.service.PlayerService;

@Service
public class TokenValidator {

    private final AdminService adminService;
    private final PlayerService playerService;

    public TokenValidator(AdminService adminService, PlayerService playerService) {
        this.adminService = adminService;
        this.playerService = playerService;
    }

    public int getTokenVersion(String role, String subject) {
        if ("ROLE_ADMIN".equals(role)) {
            return adminService.getTokenVersion(subject);
        } else if ("ROLE_PLAYER".equals(role)) {
            return playerService.getTokenVersion(Long.valueOf(subject));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown role: " + role);
        }
    }

    public void bumpTokenVersion(String role, String subject) {
        if ("ROLE_ADMIN".equals(role)) {
            adminService.bumpTokenVersion(subject);
        } else if ("ROLE_PLAYER".equals(role)) {
            playerService.bumpTokenVersion(Long.valueOf(subject));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown role: " + role);
        }
    }
}