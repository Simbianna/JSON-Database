package tasks.task2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 50, InetAddress.getByName(SERVER_ADDRESS))) {
            System.out.println("Server started!");

            try (Socket socket = serverSocket.accept();
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                 DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

                String msg = inputStream.readUTF();

                String record = msg.replaceAll("\\D", "");

                System.out.println("Received: " + msg);
                outputStream.writeUTF("A record # " + record + " was sent!");

                System.out.print("Sent: A record # " + record + " was sent!");

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
