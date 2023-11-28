package com.everyware.model.expo.booth.booth.repository;

import com.everyware.model.expo.booth.booth.entity.Booth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Long> {
    Optional<Booth> findById(Long id);
}
