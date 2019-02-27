import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void startNetwork(Socket socket) {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Scanner userIn = new Scanner(System.in);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String outgoingMessage = userIn.nextLine();
                            out.writeUTF(outgoingMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                String incomingMessage = in.readUTF();
                System.out.println(incomingMessage);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
