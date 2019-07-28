package ru.vdovgopyaty.cloud.common.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
