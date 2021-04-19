package tasks.task6.server;

import tasks.task6.pack.db.JsonDbFile;
import tasks.task6.pack.util.RequestObject;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static tasks.task6.pack.db.JsonDbProcessor.*;

import static tasks.task6.pack.util.MessageProcessor.getRoFromReceivedData;


public class Server {
    private String address;
    private int port;
    private JsonDbFile database;
    private static final String dbFileAddress = System.getProperty("user.dir") + "/src/main/java/tasks/task6/server/data/db.json";

    public Server(String address, int port, JsonDbFile database) {
        this.address = address;
        this.port = port;
        this.database = database;
    }

    public static Server createDefaultServer() {
        return new Server("127.0.0.1", 23456, new JsonDbFile(dbFileAddress));
    }


    public void run() {


        try /*(ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address)))*/ {
            ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
            System.out.println("Server started!");
            ExecutorService executor = Executors.newSingleThreadExecutor();

            while (!serverSocket.isClosed()) {

                try {
                    Socket socket = serverSocket.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    executor.submit(() -> {
                                try {
                                    String msg = inputStream.readUTF();
                                    RequestObject request = getRoFromReceivedData(msg);


                                    String response = processRequestObject(request, database);


                                    outputStream.writeUTF(response);

                                    outputStream.flush();

                                    if (request.getType() != null && request.getType().toUpperCase().equals("EXIT")) {
                                        executor.shutdown();
                                        socket.close();
                                        serverSocket.close();
                                    }

                                } catch (IOException ioe) {
                                    //   ioe.printStackTrace();
                                }
                            }

                    );

                } catch (IOException se) {
                    se.printStackTrace();
                }
            }

        } catch (IOException uhe) {
            uhe.printStackTrace();
        }
    }
}
