package com.everyware.model.expo.service;

import com.everyware.common.exception.BoothHandler;
import com.everyware.common.response.status.ErrorStatus;
import com.everyware.model.expo.repository.ExpoRepository;
import com.everyware.model.expo.booth.booth.dto.BoothResponseDTO;
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
        Expo expo = expoRepository.findById(id).orElseThrow(() -> new BoothHandler(ErrorStatus.BOOTH_NOT_EXIST));
        return expo.getBooths().stream().map(BoothResponseDTO::from).collect(Collectors.toList());
    }
}
