package tasks.task3.main;

import com.beust.jcommander.JCommander;
import tasks.task3.MainArgs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientStart {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        MainArgs mainArgs = new MainArgs();

        JCommander.newBuilder()
                .addObject(mainArgs)
                .build()
                .parse(args);

        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started!");
            String msg = mainArgs.getArgsAsString();

            outputStream.writeUTF(msg);
            System.out.println("Sent: " + msg);
            System.out.println("Received: " + inputStream.readUTF());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
