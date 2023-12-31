package com.everyware.model.expo.booth.booth.dto;


import com.everyware.model.expo.booth.booth.entity.BoothMaterial;
import com.everyware.model.expo.booth.booth.entity.BoothMaterialType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoothMaterialResponseDTO {
    private String link;
    private BoothMaterialType type;

    public static BoothMaterialResponseDTO from(BoothMaterial boothMaterial) {
        return BoothMaterialResponseDTO.builder()
                .link(boothMaterial.getLink())
                .type(boothMaterial.getType())
                .build();
    }

}
