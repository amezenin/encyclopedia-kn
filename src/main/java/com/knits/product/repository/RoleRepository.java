package com.knits.product.repository;

import com.knits.product.entity.Article;
import com.knits.product.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //List<Role> findByUserId(long userId);
}
