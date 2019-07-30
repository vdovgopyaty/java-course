package ru.vdovgopyaty.cloud.server.authservice;

public interface AuthService {

    Integer getUserId(String login, String password);

    String getToken(int id);

    String createToken(int id);
}
