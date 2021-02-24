package tasks.task3.main;


import tasks.task3.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStart {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        Server server = new Server(SERVER_ADDRESS, SERVER_PORT);

        try (ServerSocket serverSocket = new ServerSocket(server.getPort(), 50, InetAddress.getByName(server.getAddress()))) {
            System.out.println("Server started!");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

                    String msg = inputStream.readUTF();
                    String response = server.performAndGetResponse(msg);

                    outputStream.writeUTF(response);
                    if (response.equals("EXIT")) {
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
