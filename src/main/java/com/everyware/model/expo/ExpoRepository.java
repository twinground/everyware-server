package com.everyware.model.expo;

import com.everyware.model.expo.entity.Expo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpoRepository extends JpaRepository<Expo,Long> {
    Page<Expo> findAll(Pageable pageable);
}
