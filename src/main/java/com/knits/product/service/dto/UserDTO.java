package com.knits.product.service.dto;

import lombok.Data;

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
