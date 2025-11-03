package com.trader.shared.dto.mail.update;

import java.time.LocalDate;

import com.trader.shared.enums.UpdateStatus;


public class UpdateResponse {

    private Long id;
    private String title;
    private String description;
    private UpdateStatus status;
    private LocalDate plannedReleaseDate;
    private LocalDate releasedAt;

    public UpdateResponse() {
    }

    public UpdateResponse(Long id, String title, String description,
            UpdateStatus status, LocalDate plannedReleaseDate, LocalDate releasedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.plannedReleaseDate = plannedReleaseDate;
        this.releasedAt = releasedAt;
    }

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

    public LocalDate getPlannedReleaseDate() {
        return plannedReleaseDate;
    }

    public void setPlannedReleaseDate(LocalDate plannedReleaseDate) {
        this.plannedReleaseDate = plannedReleaseDate;
    }

    public LocalDate getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(LocalDate releasedAt) {
        this.releasedAt = releasedAt;
    }
}