package com.knits.product.service.mapper;

import com.knits.product.model.Article;
import com.knits.product.model.User;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleMapper {

    public Article toEntity(ArticleDTO dto) {
        if (dto == null) {
            return null;
        }

        Article entity = new Article();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCreatedDate(dto.getCreatedDate());
        //entity.setUser(dto.getUser());
        return entity;
    }

    public ArticleDTO toDto(Article entity) {

        if (entity == null) {
            return null;
        }
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        //dto.setUser(entity.getUser());
        return dto;
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

        List<ArticleDTO> list = new ArrayList<>(entityList.size());
        for (Article entity : entityList) {
            list.add(toDto(entity));
        }
        return list;
    }

    public List<Article> toEntity(List<ArticleDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Article> list = new ArrayList<>(dtoList.size());
        for (ArticleDTO articleDTO : dtoList) {
            list.add(toEntity(articleDTO));
        }
        return list;
    }
}
