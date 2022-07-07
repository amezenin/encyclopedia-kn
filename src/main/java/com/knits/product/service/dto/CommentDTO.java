package com.knits.product.service.dto;

import com.knits.product.model.Article;
import com.knits.product.model.User;
import com.knits.product.model.VoteComment;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentDTO {

    private Long id;
    private String content;
    private Date createdDate = new Date();
    //private User user;
    private Long articleId;
    private Long userId;
    //private Article article;
    //List<VoteComment> voteCommentList = new ArrayList<>();
}
