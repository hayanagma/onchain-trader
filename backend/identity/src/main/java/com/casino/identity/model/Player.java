package com.casino.identity.model;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "isbanned", nullable = false)
    private boolean isBanned = false;

    @Column
    private String bannedReason;

    @Column(nullable = false)
    private Integer tokenVersion = 1;

    @Column
    private Instant lastUsernameChangeAt;

    public Player() {
    }

    public Player(String username) {
        this.username = username;
        this.isBanned = false;
        this.bannedReason = null;
        this.tokenVersion = 1;
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
        return isBanned;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public void setBannedReason(String bannedReason) {
        this.bannedReason = bannedReason;
    }

    public Integer getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(Integer tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    public Instant getLastUsernameChangeAt() {
        return lastUsernameChangeAt;
    }

    public void setLastUsernameChangeAt(Instant lastUsernameChangeAt) {
        this.lastUsernameChangeAt = lastUsernameChangeAt;
    }

}
