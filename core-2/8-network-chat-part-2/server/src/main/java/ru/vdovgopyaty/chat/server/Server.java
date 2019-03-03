package ru.vdovgopyaty.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<Client> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server listening on port " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Client(this, socket);
                System.out.println("New client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server closed");
    }

    public void broadcastMsg(String msg) {
        for (Client client : clients) {
            client.sendMsg(msg);
        }
    }

    public void privateMsg(Client sender, String receiverNick, String msg) {
        for (Client client : clients) {
            if (client.getNickname().equals(receiverNick)) {
                client.sendMsg("From " + sender.getNickname() + ": " + msg);
                sender.sendMsg("To " + receiverNick + ":" + msg);
                return;
            }
        }
    }

    public void subscribe(Client client) {
        clients.add(client);
    }

    public void unsubscribe(Client client) {
        clients.remove(client);
    }

    public boolean isNickBusy(String nickname) {
        for (Client o : clients) {
            if (o.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}
