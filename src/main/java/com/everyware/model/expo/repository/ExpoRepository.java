package com.everyware.model.expo.repository;

import com.everyware.model.expo.entity.Expo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpoRepository extends JpaRepository<Expo,Long> {
    Page<Expo> findAll(Pageable pageable);
    Optional<Expo> findById(Long id);
}
