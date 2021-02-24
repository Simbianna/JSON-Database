package tasks.task3.main;

import tasks.task3.util.MainArgs;

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

    public static Client getDefaultClient(){
        return new Client("127.0.0.1", 23456);
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

    public void run(MainArgs mainArgs){
        try (Socket socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
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
