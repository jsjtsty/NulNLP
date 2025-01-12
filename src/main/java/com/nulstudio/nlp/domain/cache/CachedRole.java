package com.nulstudio.nlp.domain.cache;

import com.nulstudio.nlp.entity.NulPermission;
import com.nulstudio.nlp.entity.NulRole;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CachedRole implements Serializable {
    private Integer id;
    private String name;
    private NulRole.Status status;
    private List<String> permissions;

    private CachedRole() {}

    public CachedRole(@NotNull NulRole role) {
        this.id = role.getId();
        this.name = role.getName();
        this.status = role.getStatus();

        final List<String> permissions = new ArrayList<>();
        for (final NulPermission permission : role.getPermissions()) {
            permissions.add(permission.getName());
        }
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NulRole.Status getStatus() {
        return status;
    }

    public void setStatus(NulRole.Status status) {
        this.status = status;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
