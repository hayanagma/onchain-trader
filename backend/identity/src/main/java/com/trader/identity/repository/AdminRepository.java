package com.trader.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.trader.identity.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);

    @Query("SELECT a.tokenVersion FROM Admin a WHERE a.id = :id")
    int getTokenVersionById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE Admin a SET a.tokenVersion = a.tokenVersion + 1 WHERE a.id = :id")
    void bumpTokenVersionById(@Param("id") String id);

}
