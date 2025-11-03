package com.casino.mail.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.casino.mail.model.AppUpdate;
import com.casino.mail.repository.AppUpdateRepository;
import com.trader.shared.dto.mail.update.UpdateCreate;
import com.trader.shared.dto.mail.update.UpdateEditRequest;
import com.trader.shared.dto.mail.update.UpdateResponse;
import com.trader.shared.enums.UpdateStatus;

@Service
public class UpdateService {

    private final AppUpdateRepository appUpdateRepository;

    public UpdateService(AppUpdateRepository appUpdateRepository) {
        this.appUpdateRepository = appUpdateRepository;
    }

    public void create(UpdateCreate request) {
        AppUpdate update = new AppUpdate();
        update.setTitle(request.getTitle());
        update.setDescription(request.getDescription());
        update.setStatus(request.getStatus());
        update.setPlannedReleaseDate(request.getPlannedReleaseDate());
        update.setReleasedAt(request.getReleasedAt());
        update.setCreatedAt(LocalDateTime.now());
        update.setUpdatedAt(LocalDateTime.now());
        appUpdateRepository.save(update);
    }

    public List<UpdateResponse> getAllUpdates() {
        return appUpdateRepository.findAll().stream()
                .map(u -> new UpdateResponse(
                        u.getId(),
                        u.getTitle(),
                        u.getDescription(),
                        u.getStatus(),
                        u.getPlannedReleaseDate(),
                        u.getReleasedAt()))
                .collect(Collectors.toList());
    }

    public void edit(UpdateEditRequest request) {
        AppUpdate update = appUpdateRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Update not found"));

        Optional.ofNullable(request.getTitle()).ifPresent(update::setTitle);
        Optional.ofNullable(request.getDescription()).ifPresent(update::setDescription);
        Optional.ofNullable(request.getStatus()).ifPresent(update::setStatus);
        Optional.ofNullable(request.getReleasedAt()).ifPresent(update::setReleasedAt);

        if (request.getStatus() == UpdateStatus.RELEASED) {
            update.setPlannedReleaseDate(null);
        } else {
            update.setPlannedReleaseDate(request.getPlannedReleaseDate());
        }

        update.setUpdatedAt(LocalDateTime.now());
        appUpdateRepository.save(update);
    }
}
