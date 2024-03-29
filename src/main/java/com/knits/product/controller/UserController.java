package com.knits.product.controller;


import com.knits.product.exceptions.UserException;
import com.knits.product.service.UserService;
import com.knits.product.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<UserDTO> getUserById( @PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get User : {}", id);
        UserDTO userFound = userService.findById(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }


    @GetMapping(value = "/users/all", produces = {"application/json"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.debug("REST request to get all Users");
        return ResponseEntity
                .ok()
                .body(userService.findAll());
    }


    @PostMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        log.debug("REST request to createUser User ");
        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(userService.save(userDTO));
    }

    @PutMapping(value = "/users", produces = {"application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<UserDTO> updateUser( @RequestBody UserDTO userDTO) {
        log.debug("REST request to updateUser User ");
        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(userService.update(userDTO));
    }

    @PatchMapping(value = "/users/{id}",  produces = {"application/json"},
            consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CrossOrigin
    public ResponseEntity<UserDTO> partialUpdateUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody UserDTO userDTO){
        log.debug("REST request to updateUser User ");

        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        userDTO.setId(id);
        return ResponseEntity
                .ok()
                .body(userService.partialUpdate(userDTO));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        throw new UnsupportedOperationException("getAllUsers(Pageable pageable) not implemented");
    }

}
