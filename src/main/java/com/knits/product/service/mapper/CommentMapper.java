package com.knits.product.service.mapper;

import com.knits.product.model.Article;
import com.knits.product.model.Comment;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public Comment toEntity(CommentDTO dto) {
        if (dto == null) {
            return null;
        }

        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setCreatedDate(dto.getCreatedDate());
        //entity.setArticle(dto.getArticle());
        //entity.setUser(dto.getUser());
        return entity;
    }

    public CommentDTO toDto(Comment entity) {

        if (entity == null) {
            return null;
        }
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setArticleId(entity.getArticle().getId());
        dto.setUserId(entity.getUser().getId());
        //dto.setUser(entity.getUser());
        return dto;
    }

    public void partialUpdate(Comment entity, CommentDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getContent() != null) {
            entity.setContent(dto.getContent());
        }
    }

    public void update(Comment entity, CommentDTO dto) {
        if (dto == null) {
            return;
        }
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        //date
        //user
    }

    public List<CommentDTO> toDto(List<Comment> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<>(entityList.size());
        for (Comment entity : entityList) {
            list.add(toDto(entity));
        }
        return list;
    }

    public List<Comment> toEntity(List<CommentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Comment> list = new ArrayList<>(dtoList.size());
        for (CommentDTO commentDTO : dtoList) {
            list.add(toEntity(commentDTO));
        }
        return list;
    }
}
