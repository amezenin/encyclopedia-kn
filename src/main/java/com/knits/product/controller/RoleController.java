package com.knits.product.controller;

import com.knits.product.exceptions.UserException;
import com.knits.product.service.RoleService;
import com.knits.product.service.UserService;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.RoleDTO;
import com.knits.product.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(value = "/roles/{id}", produces = {"application/json"})
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get Role : {}", id);
        RoleDTO roleFound = roleService.findById(id);
        return ResponseEntity
                .ok()
                .body(roleFound);
    }


    @GetMapping(value = "/roles/all", produces = {"application/json"})
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        log.debug("REST request to get all Users");
        return ResponseEntity
                .ok()
                .body(roleService.findAll());
    }


    @PostMapping(value = "/roles", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        log.debug("REST request to createUser User ");
        if (roleDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(roleService.save(roleDTO));
    }

    @PutMapping(value = "/roles", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<RoleDTO> updateUser( @RequestBody RoleDTO roleDTO) {
        log.debug("REST request to updateUser User ");
        if (roleDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(roleService.update(roleDTO));
    }

    @PatchMapping(value = "/roles/{id}",  produces = {"application/json"}, consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RoleDTO> partialUpdateUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody RoleDTO roleDTO){
        log.debug("REST request to updateRole Role ");

        if (roleDTO == null) {
            throw new UserException("Role data are missing");
        }
        roleDTO.setId(id);
        return ResponseEntity
                .ok()
                .body(roleService.partialUpdate(roleDTO));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @GetMapping("/users/{userId}/roles")
    public List<RoleDTO> getRolesByUserId(@PathVariable(value = "userId") Long userId){
        return roleService.getRolesByUserId(userId);
    }
     */

}
