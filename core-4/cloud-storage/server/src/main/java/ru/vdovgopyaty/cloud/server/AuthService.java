package ru.vdovgopyaty.cloud.server;

public interface AuthService {

    Integer getUserId(String login, String password);

    String getToken(int id);

    String createToken(int id);
}
