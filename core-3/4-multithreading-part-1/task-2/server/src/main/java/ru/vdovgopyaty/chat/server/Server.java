package ru.vdovgopyaty.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Vector<Client> clients;
    private AuthService authService;
    private ExecutorService clientsExecutorService;

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        clients = new Vector<>();

        if (!Database.connect()) {
            throw new RuntimeException("Unable to connect to the database");
        }

        authService = new DatabaseAuthService();
        clientsExecutorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Client(this, socket);
                System.out.println("Client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientsExecutorService.shutdown();
            Database.disconnect();
            System.out.println("Server closed");
        }
    }

    public ExecutorService getClientsExecutorService() {
        return clientsExecutorService;
    }

    public void broadcastMsg(String msg) {
        for (Client client : clients) {
            client.sendMsg(msg);
        }
    }

    public void privateMsg(Client sender, String receiverNickname, String msg) {
        if (sender.getNickname().equals(receiverNickname)) {
            sender.sendMsg("Note: " + msg);
            return;
        }
        for (Client client : clients) {
            if (client.getNickname().equals(receiverNickname)) {
                client.sendMsg(sender.getNickname() + " whispered" + ": " + msg);
                sender.sendMsg("Whisper to " + receiverNickname + ": " + msg);
                return;
            }
        }
        sender.sendMsg("Client " + receiverNickname + " not found");
    }

    public void subscribe(Client Client) {
        clients.add(Client);
        broadcastClientsList();
    }

    public void unsubscribe(Client Client) {
        clients.remove(Client);
        broadcastClientsList();
    }

    public boolean isNickBusy(String nickname) {
        for (Client client : clients) {
            if (client.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder(15 * clients.size());
        sb.append("/clients ");
        // '/clients '
        for (Client client : clients) {
            sb.append(client.getNickname()).append(" ");
        }
        // '/clients nick1 nick2 nick3 '
        sb.setLength(sb.length() - 1);
        // '/clients nick1 nick2 nick3'
        String out = sb.toString();
        for (Client client : clients) {
            client.sendMsg(out);
        }
    }
}
