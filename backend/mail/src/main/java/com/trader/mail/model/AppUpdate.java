package com.trader.mail.model;

import com.trader.shared.enums.UpdateStatus;

import jakarta.persistence.*;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "updates")
public class AppUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UpdateStatus status; // PLANNED, IN_PROGRESS, RELEASED, CANCELLED

    @Column
    private java.time.LocalDate plannedReleaseDate;

    @Column
    private java.time.LocalDate releasedAt;

    @Column(nullable = false)
    private java.time.LocalDateTime createdAt;

    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateStatus getStatus() {
        return status;
    }

    public void setStatus(UpdateStatus status) {
        this.status = status;
    }

    public java.time.LocalDate getPlannedReleaseDate() {
        return plannedReleaseDate;
    }

    public void setPlannedReleaseDate(java.time.LocalDate plannedReleaseDate) {
        this.plannedReleaseDate = plannedReleaseDate;
    }

    public java.time.LocalDate getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(java.time.LocalDate releasedAt) {
        this.releasedAt = releasedAt;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}