package com.everyware.model.expo.booth.comment;

import com.everyware.model.expo.booth.comment.entity.Comment;
import java.util.List;

import com.everyware.model.expo.booth.booth.entity.Booth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBooth(Booth booth);

    Long countByBooth(Booth booth);
}
