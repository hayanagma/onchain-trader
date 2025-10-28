package com.trader.identity.model;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "traders")
public class Trader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "is_banned", nullable = false)
    private boolean banned = false;

    @Column(name = "banned_reason")
    private String bannedReason;

    @Column(nullable = false)
    private int tokenVersion = 1;

    @Column(name = "last_username_change_at")
    private Instant lastUsernameChangeAt;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean subscribed = false;

    public Trader() {
    }

    public Trader(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public void setBannedReason(String bannedReason) {
        this.bannedReason = bannedReason;
    }

    public int getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(int tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    public Instant getLastUsernameChangeAt() {
        return lastUsernameChangeAt;
    }

    public void setLastUsernameChangeAt(Instant lastUsernameChangeAt) {
        this.lastUsernameChangeAt = lastUsernameChangeAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}