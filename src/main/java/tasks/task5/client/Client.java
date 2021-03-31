package tasks.task5.client;


import tasks.task5.pack.util.ArgsEntity;
import tasks.task5.pack.util.MessageProcessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        Socket socket;
        DataInputStream inputStream;
        DataOutputStream outputStream;
        try /*(Socket socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())
        ) */ {
            socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("Client started!");

            String msg = MessageProcessor.getJsonFromArgsEntity(argsEntity);

            outputStream.writeUTF(msg);

            System.out.println("Sent: " + msg);

         //   String received = "";

        /*    while (inputStream.available() > 0){
                received = inputStream.readUTF();
            }*/

        String   received = inputStream.readUTF();

           /* while (inputStream.available() > 0) {
                 received = inputStream.readUTF();
                System.out.println("Received: " + received);
            }*/

            System.out.println("Received: " + received);
            outputStream.close();
            inputStream.close();
            socket.close();
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
