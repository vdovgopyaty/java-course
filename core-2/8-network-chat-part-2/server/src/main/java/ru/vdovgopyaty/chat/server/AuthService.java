package ru.vdovgopyaty.chat.server;

public interface AuthService {
    String getNickname(String login, String password);
}
