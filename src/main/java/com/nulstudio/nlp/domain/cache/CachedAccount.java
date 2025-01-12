package com.nulstudio.nlp.domain.cache;

import com.nulstudio.nlp.entity.NulAccount;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CachedAccount implements NulCachedEntity<NulAccount> {
    private long id;
    private String username;
    private String name;
    private String password;
    private int roleId;

    public CachedAccount() {}

    public CachedAccount(@NotNull NulAccount account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.name = account.getName();
        this.password = account.getPassword();
        this.roleId = account.getRoleId();
    }

    @Override
    @NotNull
    public NulAccount restore() {
        final NulAccount account = new NulAccount();
        account.setId(id);
        account.setUsername(username);
        account.setName(name);
        account.setPassword(password);
        account.setRoleId(roleId);
        return account;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
