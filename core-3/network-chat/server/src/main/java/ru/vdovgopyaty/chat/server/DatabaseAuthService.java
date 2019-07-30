package ru.vdovgopyaty.chat.server;

public class DatabaseAuthService implements AuthService {

    public DatabaseAuthService() {
        Database.createNewDatabase();
        if (!Database.connect()) {
            throw new RuntimeException("Unable to connect to the database");
        }

        seedUsers();
    }

    @Override
    public String getNickname(String login, String password) {
        return Database.getUserNickname(login, password);
    }

    @Override
    public boolean changeNickname(String currentNickname, String newNickname) {
        return Database.changeUserNickname(currentNickname, newNickname);
    }

    private void seedUsers() {
        Database.createUser("user1", "pass1", "Alice");
        Database.createUser("user2", "pass2", "Bob");
        Database.createUser("user3", "pass3", "Walle");
        System.out.println("Users seeded");
    }
}
