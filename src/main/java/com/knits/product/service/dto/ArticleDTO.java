package com.knits.product.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knits.product.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate = new Date();
    private Long userId;
    //private User user;
    private List<CommentDTO> commentList = new ArrayList<>();
    private List<UserDTO> likes;
    private Integer likesCount;

}
