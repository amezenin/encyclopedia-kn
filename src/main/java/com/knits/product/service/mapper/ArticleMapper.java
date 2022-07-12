package com.knits.product.service.mapper;

import com.knits.product.entity.Article;
import com.knits.product.service.dto.ArticleDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

    private final ModelMapper modelMapper;

    public Article toEntity(ArticleDTO dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Article.class);
    }

    public ArticleDTO toDto(Article entity) {

        if (entity == null) {
            return null;
        }

        return modelMapper.map(entity, ArticleDTO.class);
    }

    public void partialUpdate(Article entity, ArticleDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null) {
            entity.setContent(dto.getContent());
        }
        /*if (dto.getCreatedDate() != null) {
            entity.setCreatedDate(dto.getCreatedDate());
        }*/

        /*if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }*/
    }

    public void update(Article entity, ArticleDTO dto) {
        if (dto == null) {
            return;
        }
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        //date
        //user
    }

    public List<ArticleDTO> toDto(List<Article> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Article> toEntity(List<ArticleDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
