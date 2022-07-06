package com.knits.product.service.dto;

import com.knits.product.model.Article;
import com.knits.product.model.Comment;
import com.knits.product.model.VoteArticle;
import com.knits.product.model.VoteComment;
import lombok.Data;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean active = true;
    //private List<ArticleDTO> articleDTOList = new ArrayList<>();
    //private List<Comment> commentList = new ArrayList<>();
    //private List<VoteArticle> voteArticleList = new ArrayList<>();
    //private List<VoteComment> voteCommentList = new ArrayList<>();
}
