package ru.vdovgopyaty.cloud.server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleAuthService implements AuthService {

    private class UserData {
        private String login;
        private String password;
        private String token;

        UserData(String login, String password) {
            this.login = login;
            this.password = password;
            this.token = UUID.randomUUID().toString();
        }
    }

    private List<UserData> users;

    public SimpleAuthService() {
        this.users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            users.add(new UserData("user" + i, "pass" + i));
        }
    }

    @Override
    public String getToken(String login, String password) {
        for (UserData user : users) {
            if (user.login.equals(login)) {
                if (user.password.equals(password)) {
                    user.token = UUID.randomUUID().toString();
                    return user.token;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public String getLoginByToken(String token) {
        for (UserData user : users) {
            if (user.token.equals(token)) {
                return user.login;
            }
        }
        return null;
    }
}
