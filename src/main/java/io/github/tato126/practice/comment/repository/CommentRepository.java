package io.github.tato126.practice.comment.repository;

import io.github.tato126.practice.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
