package com.trader.identity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.identity.service.AdminService;
import com.trader.shared.dto.identity.admin.AdminResponse;


@RestController
@RequestMapping("/api/internal/identity/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<AdminResponse> findByUsername(@PathVariable String username) {
        return adminService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
