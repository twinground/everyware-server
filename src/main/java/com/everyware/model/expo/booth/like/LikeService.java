package com.everyware.model.expo.booth.like;

import com.everyware.model.expo.booth.like.entity.Like;
import com.everyware.model.expo.booth.booth.service.BoothService;
import com.everyware.model.expo.booth.booth.entity.Booth;
import com.everyware.model.member.Member;
import com.everyware.model.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoothService boothService;
    private final UserService userService;

    public boolean addLike(Long boardId, String email) {

        Member member = userService.findByEmail(email);
        Booth booth = boothService.findById(boardId);
        if (!likeRepository.existsByMemberAndBooth(member, booth)) {
            booth.setLikeCount(booth.getLikeCount() + 1);
            likeRepository.save(new Like(member, booth));
            return true;
        } else {
            booth.setLikeCount(booth.getLikeCount() - 1);
            likeRepository.deleteByMemberAndBooth(member, booth);
            return false;
        }

    }

}
