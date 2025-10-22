package com.casino.identity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.casino.identity.service.AdminService;

@Component
public class Initializer implements CommandLineRunner {

    private final AdminService adminService;

    public Initializer(AdminService adminService) {
        this.adminService = adminService;
    }

    @Value("${APP_ADMIN_0_USERNAME}")
    private String adminUsername;

    @Value("${APP_ADMIN_0_PASSWORD}")
    private String adminPassword;

    @Value("${APP_ADMIN_1_USERNAME}")
    private String adminUsernameTwo;

    @Value("${APP_ADMIN_1_PASSWORD}")
    private String adminPasswordTwo;

    @Override
    public void run(String... args) {
        adminService.createOrUpdate(adminUsername, adminPassword);
        adminService.createOrUpdate(adminUsernameTwo, adminPasswordTwo);
    }
}