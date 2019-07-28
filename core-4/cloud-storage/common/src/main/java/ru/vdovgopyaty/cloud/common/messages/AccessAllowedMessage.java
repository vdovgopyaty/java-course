package ru.vdovgopyaty.cloud.common.messages;

public class AccessAllowedMessage extends Message {
    private String token;

    public AccessAllowedMessage(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
