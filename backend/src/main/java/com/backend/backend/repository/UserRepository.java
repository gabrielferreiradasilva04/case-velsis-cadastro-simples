package com.backend.backend.repository;

import com.backend.backend.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Page<UserEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByDocument(String document);
}
