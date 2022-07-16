package com.knits.product.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long userId;
    private List<CommentDTO> commentList = new ArrayList<>();
    private List<UserDTO> likes;
    private Integer likesCount;

}
