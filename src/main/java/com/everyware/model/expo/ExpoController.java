package com.everyware.model.expo;

import com.everyware.model.expo.dto.ExpoBoothsResponseDTO;
import com.everyware.model.expo.dto.ExpoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RequestMapping("/api/expos")
@RequiredArgsConstructor
public class ExpoController {

    private final ExpoService expoService;

    @Operation(summary = "엑스포들 조회  API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "엑스포 조회 성공한 경우"),
    })
    @GetMapping
    public ResponseEntity<Page<ExpoResponseDTO>> getExpos(
            @PageableDefault(size = 8, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(expoService.getExpos(pageable));
    }

    /*
    @Operation(summary = "엑스포 내부 부스 조회 API")
    @ApiResponse(responseCode = "200", description = "부스들 조회 성공한 경우")
    @GetMapping
    public ResponseEntity<ExpoBoothsResponseDTO> getExpoBooths(){
        //return ResponseEntity.ok(expoService)
    }*/


}
