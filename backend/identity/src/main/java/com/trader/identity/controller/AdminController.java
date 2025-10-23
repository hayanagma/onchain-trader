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
import com.trader.identity.service.PlayerService;
import com.trader.shared.dto.identity.admin.AdminPlayerInternalResponse;
import com.trader.shared.dto.identity.admin.AdminResponse;
import com.trader.shared.dto.identity.admin.BanRequest;

@RestController
@RequestMapping("/api/internal/identity/admin")
public class AdminController {
    private final AdminService adminService;
    private final PlayerService playerService;

    public AdminController(AdminService adminService, PlayerService playerService) {
        this.adminService = adminService;
        this.playerService = playerService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<AdminResponse> findByUsername(@PathVariable String username) {
        return adminService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/players/ban-status")
    public ResponseEntity<Void> updateBanStatus(@RequestBody BanRequest request) {
        playerService.setBanStatus(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/players")
    public List<AdminPlayerInternalResponse> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/players/{id}")
    public AdminPlayerInternalResponse getPlayer(@PathVariable Long id) {
        return playerService.getAdminPlayerById(id);
    }

}
