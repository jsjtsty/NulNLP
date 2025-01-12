package com.nulstudio.nlp.domain.vo;

import org.jetbrains.annotations.NotNull;

public final class AccountProfileVo {
    private long uid;
    private String username;
    private String name;
    private String role;

    public AccountProfileVo() {}

    public AccountProfileVo(long uid, @NotNull String username, @NotNull String name, @NotNull String role) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
