package ru.vdovgopyaty.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private String nickname;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    public String getNickname() {
        return nickname;
    }

    public Client(Server server, Socket socket) {
        logger.setLevel(Level.ALL);
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/auth ")) {
                            logger.log(Level.FINE, "User is trying to authenticate");
                            String[] tokens = msg.split("\\s");
                            String nickname = server.getAuthService().getNickname(tokens[1], tokens[2]);
                            if (nickname != null && !server.isNickBusy(nickname)) {
                                sendMsg("/auth:succeeded " + nickname);
                                this.nickname = nickname;
                                server.subscribe(this);
                                break;
                            }
                            logger.log(Level.FINE, "User not authenticated");
                        }
                    }
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            if (msg.equals("/end")) {
                                sendMsg("/end");
                                break;
                            }
                            if (msg.startsWith("/w ")) {
                                String[] tokens = msg.split("\\s", 3);
                                server.privateMsg(this, tokens[1], tokens[2]);
                            }
                            if (msg.startsWith("/changenick ")) {
                                logger.log(Level.FINER,"User " + this.nickname + " is trying to change nickname");
                                String newNickname = msg.split("\\s", 2)[1];
                                if (!newNickname.matches("([a-zA-Z]+[0-9]*)|([а-яА-Я]+[0-9]*)")) {
                                    sendMsg("/changenick:error Nickname can contain only letters and numbers");
                                    logger.log(Level.FINER,"User " + this.nickname +
                                            "'s new nickname contains invalid characters");
                                    continue;
                                }
                                if (server.getAuthService().changeNickname(this.nickname, newNickname)) {
                                    logger.log(Level.FINER,"User " + this.nickname +
                                            " changed nickname to " + newNickname);
                                    this.nickname = newNickname;
                                    sendMsg("/changenick:succeeded " + nickname);
                                    server.broadcastClientsList();
                                } else {
                                    sendMsg("/changenick:error Nickname is already taken");
                                    logger.log(Level.FINER,"User " + this.nickname +
                                            "'s new nickname is already taken");
                                }
                            }
                        } else {
                            server.broadcastMsg(this.nickname, msg);
                        }
                    }
                } catch (IOException e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
                } finally {
                    Client.this.disconnect();
                }
            }).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            in.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        try {
            out.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
