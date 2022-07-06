package com.knits.product.service.dto;

import com.knits.product.model.Comment;
import com.knits.product.model.User;
import com.knits.product.model.VoteArticle;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private Date createdDate = new Date();
    //here User object or Long userId? or nothing if use user id in link
    //private User user;
    //private List<Comment> commentList = new ArrayList<>();
    //private List<VoteArticle> voteArticleList = new ArrayList<>();
}
