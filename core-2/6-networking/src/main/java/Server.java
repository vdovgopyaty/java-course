import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Socket socket = null;

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server listening on port " + serverSocket.getLocalPort() + "...");
            socket = serverSocket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.startNetwork(socket);
    }
}
