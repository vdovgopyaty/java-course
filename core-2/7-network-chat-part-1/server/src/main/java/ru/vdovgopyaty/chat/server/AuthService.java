package ru.vdovgopyaty.chat.server;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);
}
