package com.knits.product.repository;

import com.knits.product.model.Article;
import com.knits.product.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(long articleId);
}
