package com.knits.product.service.mapper;

import com.knits.product.model.Article;
import com.knits.product.model.Comment;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ModelMapper modelMapper;

    public Comment toEntity(CommentDTO dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Comment.class);
    }

    public CommentDTO toDto(Comment entity) {

        if (entity == null) {
            return null;
        }

        return modelMapper.map(entity, CommentDTO.class);
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

        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Comment> toEntity(List<CommentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
