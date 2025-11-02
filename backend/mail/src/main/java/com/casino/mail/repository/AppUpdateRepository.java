package com.casino.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casino.mail.model.AppUpdate;

@Repository
public interface AppUpdateRepository extends JpaRepository<AppUpdate, Long> {
}
