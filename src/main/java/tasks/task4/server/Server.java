package tasks.task4.server;

import tasks.task4.pack.command.CommandType;
import tasks.task4.pack.db.JsonDb;
import tasks.task4.pack.util.ConsoleEntity;
import tasks.task4.pack.util.MessageProcessor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static tasks.task4.pack.db.JsonDbProcessor.processConsoleEntity;


public class Server {
    private String address;
    private int port;
    private JsonDb database;

    public Server(String address, int port, JsonDb database) {
        this.address = address;
        this.port = port;
        this.database = database;
    }

    public static Server createDefaultServer() {
        return new Server("127.0.0.1", 23456, new JsonDb(1000));
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDatabase(JsonDb database) {
        this.database = database;
    }

    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");

            while (true) {
                try (Socket socket = serverSocket.accept();
                           DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                           DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

                        String msg = inputStream.readUTF();
                        ConsoleEntity data = MessageProcessor.getConsoleEntityFromJson(msg);

                        String response = processConsoleEntity(data, database);

                        outputStream.writeUTF(response);
                        outputStream.flush();

                        if (data.getType() == CommandType.EXIT) {
                            break;
                        }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
