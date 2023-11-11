package com.everyware.model.like;

import com.everyware.model.expo.entity.Booth;
import com.everyware.model.like.entity.Like;
import com.everyware.model.member.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByUserAndBooth(User user, Booth booth);
    void deleteByUserAndBooth(User user, Booth booth);


}
