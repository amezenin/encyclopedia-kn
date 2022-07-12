package com.knits.product.service.dto;

import lombok.Data;

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
    private Long userId;
    private List<CommentDTO> commentList = new ArrayList<>();
    private List<UserDTO> likes = new ArrayList<>();
}
