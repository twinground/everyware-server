package com.everyware.model.expo;

import com.everyware.BaseException;
import com.everyware.ErrorCode;
import com.everyware.model.expo.entity.Booth;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoothService {

    private final BoothRepository boothRepository;
    public Booth findById(Long boardId) {
        return boothRepository.findById(boardId).orElseThrow(() -> BaseException.from(ErrorCode.BOOTH_NOT_FOUND));
    }

}
