package com.nulstudio.nlp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Table(name = "nul_invite")
public class NulInvite {
    public enum Status {
        Normal, Blocked
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "invite_code", nullable = false, length = 6)
    private String inviteCode;

    @ColumnDefault("1")
    @Column(name = "remaining", nullable = false)
    private Integer remaining;

    @Column(name = "roleId", nullable = false)
    private Integer role;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;

    @Column(name = "expire_time", nullable = false)
    private Timestamp expireTime;

    @ColumnDefault("'Normal'")
    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}