package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.entity.NulInvite;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public final class InviteVo {
    private String inviteCode;
    private int remaining;
    private Instant expireTime;
    private Integer status;
    private ObjectId roleId;

    public InviteVo() {}

    public InviteVo(@NotNull NulInvite invite) {
        inviteCode = invite.getInviteCode();
        remaining = invite.getRemaining();
        expireTime = invite.getExpireTime();
        status = invite.getStatus();
        roleId = invite.getRoleId();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public Instant getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Instant expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ObjectId getRoleId() {
        return roleId;
    }

    public void setRoleId(ObjectId roleId) {
        this.roleId = roleId;
    }
}
