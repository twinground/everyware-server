package com.everyware.model.like;

import com.everyware.model.expo.BoothRepository;
import com.everyware.model.expo.BoothService;
import com.everyware.model.expo.entity.Booth;
import com.everyware.model.like.entity.Like;
import com.everyware.model.member.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoothService boothService;

    public void addLike(Long boardId, User user) {

        Booth booth = boothService.findById(boardId);
        if (!likeRepository.existsByUserAndBooth(user, booth)) {
            booth.setLikeCount(booth.getLikeCount() + 1);
            likeRepository.save(new Like(user, booth));
        } else {
            booth.setLikeCount(booth.getLikeCount() - 1);
            likeRepository.deleteByUserAndBooth(user, booth);
        }

    }

}
