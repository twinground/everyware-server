package com.everyware.model.comment;

import com.everyware.model.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoothId(Long id);
}
