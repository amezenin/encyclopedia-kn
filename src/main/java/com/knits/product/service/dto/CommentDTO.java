package com.knits.product.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentDTO {

    private Long id;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate = new Date();
    //private User user;
    private Long articleId;
    private Long userId;
    //private Article article;
    private List<UserDTO> likes = new ArrayList<>();
    private Integer likesCount;
    private Long likeUserId;
}
