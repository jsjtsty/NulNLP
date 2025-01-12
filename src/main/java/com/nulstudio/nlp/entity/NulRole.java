package com.nulstudio.nlp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nul_role")
public class NulRole {

    public enum Status {
        Normal, Blocked
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @ColumnDefault("'Normal'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "nul_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<NulPermission> permissions = new HashSet<>();

    @Lob
    @Column(name = "comment")
    private String comment;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<NulPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<NulPermission> permissions) {
        this.permissions = permissions;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}