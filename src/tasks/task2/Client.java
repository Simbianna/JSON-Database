package tasks.task2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             // Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Client started!");
            //  String msg = scanner.nextLine();

            String msg = "Give me a record # 12";

            outputStream.writeUTF(msg);
            System.out.println("Sent: " + msg);

            String serverResponse = inputStream.readUTF();
            System.out.print("Received: " + serverResponse);

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
