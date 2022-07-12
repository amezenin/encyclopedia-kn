package com.knits.product.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roleList")
    List<User> roles;

    public void addRoles(User user) {
        roles.add(user);
    }

    public void removeRoles(User user) {
        roles.remove(user);
    }
}
