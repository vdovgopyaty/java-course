package ru.vdovgopyaty.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private Vector<Client> clients;
    private AuthService authService;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        logger.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        clients = new Vector<>();

        if (!Database.connect()) {
            RuntimeException e = new RuntimeException("Unable to connect to the database");
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }

        authService = new DatabaseAuthService();

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            logger.log(Level.INFO, "Server is listening on port " + serverSocket.getLocalPort() + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Client(this, socket);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            Database.disconnect();
            logger.log(Level.INFO, "Server closed");
        }
    }

    public void broadcastMsg(String senderNickname, String msg) {
        logger.log(Level.FINEST,"User " + senderNickname + " sent a message");
        for (Client client : clients) {
            if (client.getNickname().equals(senderNickname)) {
                client.sendMsg("You: " + msg);
            } else {
                client.sendMsg(senderNickname + ": " + msg);
            }
        }
    }

    public void privateMsg(Client sender, String receiverNickname, String msg) {
        if (sender.getNickname().equals(receiverNickname)) {
            sender.sendMsg("Note: " + msg);
            logger.log(Level.FINEST,"User " + sender.getNickname() + " wrote a note");
            return;
        }
        for (Client client : clients) {
            if (client.getNickname().equals(receiverNickname)) {
                client.sendMsg(sender.getNickname() + " whispers" + ": " + msg);
                sender.sendMsg("You whisper to " + receiverNickname + ": " + msg);
                logger.log(Level.FINEST,"User " + sender.getNickname() + " sent a private message");
                return;
            }
        }
        sender.sendMsg("Client " + receiverNickname + " not found");
        logger.log(Level.FINEST,
                "User " + sender.getNickname() + " tried to send a private message to a non-existent client");
    }

    public void subscribe(Client Client) {
        clients.add(Client);
        broadcastClientsList();
        logger.log(Level.FINE, "User " + Client.getNickname() + " connected");
    }

    public void unsubscribe(Client Client) {
        clients.remove(Client);
        broadcastClientsList();
        logger.log(Level.FINE, "User " + Client.getNickname() + " disconnected");
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
