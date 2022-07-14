package com.knits.product.service;

import com.knits.product.entity.Article;
import com.knits.product.entity.Role;
import com.knits.product.entity.User;
import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.UserException;
import com.knits.product.repository.RoleRepository;
import com.knits.product.repository.UserRepository;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.RoleDTO;
import com.knits.product.service.dto.UserDTO;
import com.knits.product.service.mapper.RoleMapper;
import com.knits.product.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    private final RoleRepository roleRepository;

    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        // userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleMapper.toEntity(roleDTO);

        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    public RoleDTO findById(Long id) {
        log.debug("Request Role by id : {}", id);
        Role role = roleRepository.findById(id).orElseThrow(() -> new UserException("Role#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
        return roleMapper.toDto(role);
    }

    public RoleDTO update(RoleDTO roleDTO) {
        log.debug("Request to update Role : {}", roleDTO);
        Role role = roleRepository.findById(roleDTO.getId()).orElseThrow(() -> new UserException("Role#" + roleDTO.getId() + " not found"));
        roleMapper.update(role, roleDTO);

        //TODO: manage User relationship to check updates
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    public RoleDTO partialUpdate(RoleDTO roleDTO) {
        log.debug("Request to partially update Role : {}", roleDTO);
        Role role = roleRepository.findById(roleDTO.getId()).orElseThrow(() -> new UserException("Role#" + roleDTO.getId() + " not found"));
        roleMapper.partialUpdate(role, roleDTO);

        //TODO: manage User relationship to check updates
        roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    public void delete(Long id) {
        log.debug("Delete Role by id : {}", id);
        roleRepository.deleteById(id);
    }

    /*
    public List<RoleDTO> getRolesByUserId(long userId) {
        // retrieve articles by userId
        List<Role> roles = roleRepository.findByUserId(userId);

        // convert list of comment entities to list of comment dto's
        return roleMapper.toDto(roles);
    }
     */

    public RoleDTO findByName(String name){
        log.debug("Request Role by name : {}", name);
        Role role = roleRepository.findByName(name).orElseThrow(()
                -> new UserException("Role#" + name + " not found", ExceptionCodes.USER_NOT_FOUND));
        return roleMapper.toDto(role);
    }
}
