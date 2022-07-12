package com.knits.product.service.mapper;

import com.knits.product.entity.Role;
import com.knits.product.service.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Role.class);
    }

    public RoleDTO toDto(Role entity) {

        if (entity == null) {
            return null;
        }

        return modelMapper.map(entity, RoleDTO.class);
    }

    public void partialUpdate(Role entity, RoleDTO dto) {
        if (dto == null) {
            return;
        }
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
    }

    public void update(Role entity, RoleDTO dto) {
        if (dto == null) {
            return;
        }
        entity.setId(dto.getId());
        entity.setRole(dto.getRole());
    }

    public List<RoleDTO> toDto(List<Role> entityList) {
        if (entityList == null) {
            return null;
        }

        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Role> toEntity(List<RoleDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
