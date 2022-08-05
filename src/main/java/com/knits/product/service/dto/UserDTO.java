package com.knits.product.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knits.product.entity.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;
    private String login;
    //@JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean active = true;
    private List<RoleDTO> roles = new ArrayList<>();
    //private List<Comment> likedComments = new ArrayList<>();
    //private List<Comment> commentList = new ArrayList<>();
    //private List<VoteArticle> voteArticleList = new ArrayList<>();
    //private List<VoteComment> voteCommentList = new ArrayList<>();
}
