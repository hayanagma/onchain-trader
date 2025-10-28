package com.trader.identity.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.identity.service.AdminService;
import com.trader.identity.service.TraderService;

@Service
public class TokenValidator {

    private final AdminService adminService;
    private final TraderService traderService;

    public TokenValidator(AdminService adminService, TraderService traderService) {
        this.adminService = adminService;
        this.traderService = traderService;
    }

    public int getTokenVersion(String role, String subject) {
        if ("ROLE_ADMIN".equals(role)) {
            return adminService.getTokenVersion(subject);
        } else if ("ROLE_TRADER".equals(role)) {
            return traderService.getTokenVersion(Long.valueOf(subject));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown role: " + role);
        }
    }

    public void bumpTokenVersion(String role, String subject) {
        if ("ROLE_ADMIN".equals(role)) {
            adminService.bumpTokenVersion(subject);
        } else if ("ROLE_TRADER".equals(role)) {
            traderService.bumpTokenVersion(Long.valueOf(subject));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unknown role: " + role);
        }
    }
}