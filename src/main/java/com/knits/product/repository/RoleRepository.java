package com.knits.product.repository;

import com.knits.product.entity.Article;
import com.knits.product.entity.Role;
import com.knits.product.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    //List<Role> findByUserId(long userId);
}
