package com.nulstudio.nlp.domain.vo;

import com.nulstudio.nlp.util.NulJwtToken;

import java.io.Serializable;

public final class LoginVo implements Serializable {
    private String token;
    private long expiresIn = NulJwtToken.JWT_VALID_TIME;

    public LoginVo() {}

    public LoginVo(String token) {
        this.token = token;
    }

    public LoginVo(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
