package ru.vdovgopyaty.cloud.server;

public interface AuthService {

    String getToken(String login, String password);

    String getLoginByToken(String token);
}
