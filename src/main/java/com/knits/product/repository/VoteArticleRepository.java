package com.knits.product.repository;

import com.knits.product.model.VoteArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteArticleRepository extends JpaRepository<VoteArticle, Long> {
}
