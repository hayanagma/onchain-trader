package com.trader.auth.dto.admin;

public class AdminVerifyRequest {

    private String adminId;
    private String code;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}