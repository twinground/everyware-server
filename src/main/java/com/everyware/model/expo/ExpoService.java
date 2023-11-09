package com.everyware.model.expo;

import com.everyware.model.expo.dto.BoothResponseDTO;
import com.everyware.model.expo.dto.ExpoResponseDTO;
import com.everyware.model.expo.entity.Expo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public List<BoothResponseDTO> getExpoBooths(Long id){
        Expo expo = expoRepository.getById(id);
        return expo.getBooths().stream().map(BoothResponseDTO::from).collect(Collectors.toList());
    }
}
