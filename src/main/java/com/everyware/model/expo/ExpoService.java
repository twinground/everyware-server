package com.everyware.model.expo;

import com.everyware.model.expo.dto.ExpoResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpoService {
    private final ExpoRepository expoRepository;

    @Transactional(readOnly = true)
    public Page<ExpoResponseDTO> getExpos(Pageable pageable) {

        Page<ExpoResponseDTO> page = expoRepository.findAll(pageable).map(ExpoResponseDTO::from);

        return page;
    }
}
