package com.knits.product.service.dto;

import lombok.Data;

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
    List<UserDTO> likes = new ArrayList<>();
}
