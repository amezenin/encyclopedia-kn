package com.knits.product.service;

import com.knits.product.entity.Role;
import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.UserException;
import com.knits.product.entity.User;
import com.knits.product.repository.UserRepository;
import com.knits.product.service.dto.UserDTO;
import com.knits.product.service.mapper.RoleMapper;
import com.knits.product.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing {@link com.knits.product.entity.User}.
 */
@Service
@Transactional
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository,
                       RoleMapper roleMapper, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a user.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User : {}", userDTO);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleService.findByName("USER");
        //List<Role> roleList = new ArrayList<>();
        //roleList.add(role);
        User user = userMapper.toEntity(userDTO);
        user.addRole(role);
        user.setActive(true);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Partially updates a user.
     *
     * @param userDTO the entity to update partially.
     * @return the persisted entity.
     */
    public UserDTO partialUpdate(UserDTO userDTO) {
        log.debug("Request to partially update User : {}", userDTO);
        User user = getUserById(userDTO);
        userMapper.partialUpdate(user, userDTO);

        //TODO: manage User relationship to check updates
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * overrides fields, including nulls.
     *
     * @param userDTO the entity to update.
     * @return the persisted entity.
     */
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = getUserById(userDTO);
        userMapper.update(user, userDTO);

        //TODO: manage User relationship to check updates
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get by the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public UserDTO findById(Long id) {

        log.debug("Request User by id : {}", id);
        User user = userRepository.findById(id).orElseThrow(()
                -> new UserException("User#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    public User findByLogin(String login){
        log.debug("Request User by login : {}", login);
        User user = userRepository.findOneByLogin(login).orElseThrow(()
                -> new UserException("User#" + login + " not found", ExceptionCodes.USER_NOT_FOUND));
        log.info("IN findByLogin(Service) - user: {} found by username: {}", user, login);
        return user;
    }

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Delete User by id : {}", id);
        userRepository.deleteById(id);
    }


    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserDTO> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("not yet implementes");
    }


    //helpers
    private User getUserById(UserDTO userDTO) {
        return userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserException("User#" + userDTO.getId() + " not found"));
    }





}
