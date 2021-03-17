package tasks.task4.client;


import tasks.task4.pack.util.ArgsEntity;
import tasks.task4.pack.util.MessageProcessor;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public static Client getDefaultClient() {
        return new Client("127.0.0.1", 23456);
    }

    public void run(ArgsEntity argsEntity) {
        try (Socket socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        ) {

            System.out.println("Client started!");
            String msg = MessageProcessor.getJsonFromObject(argsEntity);

            outputStream.writeUTF(msg);

            System.out.println("Sent: " + msg);

            String received = null;
            while (received == null){
                received = inputStream.readUTF();
            }
            /*while (inputStream.available() > 0) {
                String received = inputStream.readUTF();
                System.out.println("Received: " + received);
            }*/

            System.out.println("Received: " + received);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }


}
