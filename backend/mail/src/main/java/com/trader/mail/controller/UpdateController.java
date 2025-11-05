package com.trader.mail.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trader.mail.service.UpdateService;
import com.trader.shared.dto.mail.update.UpdateCreate;
import com.trader.shared.dto.mail.update.UpdateEditRequest;
import com.trader.shared.dto.mail.update.UpdateResponse;


@RestController
@RequestMapping("/api/internal/updates")
public class UpdateController {

    private final UpdateService updateService;

    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody UpdateCreate request) {
        updateService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UpdateResponse>> getAllUpdates() {
        List<UpdateResponse> updates = updateService.getAllUpdates();
        return ResponseEntity.ok(updates);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@RequestBody UpdateEditRequest request) {
        updateService.edit(request);
        return ResponseEntity.ok().build();
    }

}
