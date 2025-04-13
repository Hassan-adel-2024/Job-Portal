package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<User> users;

    public UserRole(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
