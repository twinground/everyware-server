package com.everyware.model.like;

import com.everyware.model.expo.entity.Booth;
import com.everyware.model.like.entity.Like;
import com.everyware.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByMemberAndBooth(Member member, Booth booth);
    void deleteByMemberAndBooth(Member member, Booth booth);


}
