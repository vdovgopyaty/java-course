import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.startNetwork(socket);
    }
}
