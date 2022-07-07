package com.knits.product.service.mapper;

import com.knits.product.model.User;
import com.knits.product.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setActive(dto.getActive());
        return entity;
    }

    public UserDTO toDto(User entity) {

        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.getActive());
        return dto;
    }

    public void partialUpdate(User entity, UserDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getLogin() != null) {
            entity.setLogin(dto.getLogin());
        }
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
    }

    public void update(User entity, UserDTO dto) {
        if (dto == null) {
            return;
        }
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.getActive());
    }

    public List<UserDTO> toDto(List<User> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<User> toEntity(List<UserDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
