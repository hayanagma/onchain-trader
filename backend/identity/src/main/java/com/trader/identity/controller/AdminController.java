package com.trader.identity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.identity.service.AdminService;
import com.trader.identity.service.TraderService;
import com.trader.shared.dto.identity.admin.AdminResponse;
import com.trader.shared.dto.identity.admin.AdminTraderInternalResponse;
import com.trader.shared.dto.identity.admin.BanRequest;

@RestController
@RequestMapping("/api/internal/identity/admin")
public class AdminController {
    private final AdminService adminService;
    private final TraderService traderService;

    public AdminController(AdminService adminService, TraderService traderService) {
        this.adminService = adminService;
        this.traderService = traderService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<AdminResponse> findByUsername(@PathVariable String username) {
        return adminService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}/mail")
    public ResponseEntity<String> getMail(@PathVariable String username) {
        String mail = adminService.getAdminMail(username);
        return ResponseEntity.ok(mail);
    }

    @PutMapping("/traders/ban-status")
    public ResponseEntity<Void> updateBanStatus(@RequestBody BanRequest request) {
        traderService.setBanStatus(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/traders")
    public List<AdminTraderInternalResponse> getTraders() {
        return traderService.getTraders();
    }

    @GetMapping("/traders/{id}")
    public AdminTraderInternalResponse getTrader(@PathVariable Long id) {
        return traderService.getAdminTraderById(id);
    }
}