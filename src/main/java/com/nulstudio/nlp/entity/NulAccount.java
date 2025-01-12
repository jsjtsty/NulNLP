package com.nulstudio.nlp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nul_account")
public class NulAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 256)
    private String username;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}