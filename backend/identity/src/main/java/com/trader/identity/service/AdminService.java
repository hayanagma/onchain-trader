package com.trader.identity.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trader.identity.model.Admin;
import com.trader.identity.repository.AdminRepository;
import com.trader.shared.dto.identity.admin.AdminResponse;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createOrUpdate(String username, String rawPassword) {
        adminRepository.findByUsername(username)
                .ifPresentOrElse(
                        existing -> {
                            existing.setPassword(passwordEncoder.encode(rawPassword));
                            adminRepository.save(existing);
                        },
                        () -> {
                            Admin admin = new Admin();
                            admin.setUsername(username);
                            admin.setPassword(passwordEncoder.encode(rawPassword));
                            admin.setTokenVersion(1);
                            adminRepository.save(admin);
                        });
    }

    public int getTokenVersion(String username) {
        return getAdminEntity(username).getTokenVersion();
    }

    private Admin getAdminEntity(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Admin not found: " + username));
    }

    public void bumpTokenVersion(String username) {
        adminRepository.bumpTokenVersionById(username);
    }

    public Optional<AdminResponse> findByUsername(String username) {
        return adminRepository.findByUsername(username)
                .map(this::mapToResponse);
    }

    private AdminResponse mapToResponse(Admin admin) {
        AdminResponse response = new AdminResponse();
        response.setUsername(admin.getUsername());
        response.setPassword(admin.getPassword());
        response.setTokenVersion(admin.getTokenVersion());
        return response;
    }

}