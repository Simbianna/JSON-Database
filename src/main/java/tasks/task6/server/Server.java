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

    public void setDatabase(JsonDbFile database) {
        this.database = database;
    }


    public void run() {

        try /*(ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address)))*/ {
            ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
            System.out.println("Server started!");
            ExecutorService executor = Executors.newSingleThreadExecutor();

            try {
                Socket socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                try {
                    String msg = inputStream.readUTF();
                    //    String msg =  "{\"type\":\"set\",\"key\":\"1\",\"value\":\"Hello world!\"}";
                    RequestObject request = getRoFromReceivedData(msg);

                    System.out.println(request);

                    String response = processRequestObject(request, database);
                    System.out.println(response);

                    outputStream.writeUTF(response);

                    outputStream.flush();

                    if (request.getType() != null && request.getType().toUpperCase().equals("EXIT")) {
                        executor.shutdown();
                        serverSocket.close();
                    }

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

        } catch (SocketException se) {
            se.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


/*            while (!serverSocket.isClosed()) {

                try {
                    Socket socket = serverSocket.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    executor.submit(() -> {
                                try {
                                    String msg = inputStream.readUTF();
                               //    String msg =  "{\"type\":\"set\",\"key\":\"1\",\"value\":\"Hello world!\"}";
                                    RequestObject request = getRoFromReceivedData(msg);

                                    System.out.println(request);

                                    String response = processRequestObject(request, database);
                                    System.out.println(response);

                                    outputStream.writeUTF(response);

                                    outputStream.flush();

                                    if (request.getType() != null && request.getType().toUpperCase().equals("EXIT")) {
                                        executor.shutdown();
                                        serverSocket.close();
                                    }

                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }

                    );

                } catch (SocketException se) {
                    se.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }*/

    } catch(
    IOException uhe)

    {
        uhe.printStackTrace();
    }

}
}


/*

                executor.submit(() -> {
                    try *//*(Socket socket = serverSocket.accept();
                         DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                         DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()))*//* {
                        Socket socket = serverSocket.accept();
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                        String msg = inputStream.readUTF();
                        ConsoleEntity data = MessageProcessor.getConsoleEntityFromJson(msg);

                        String response = processConsoleEntity(data, database);

                        outputStream.writeUTF(response);

                        outputStream.flush();

                        if (data.getType() == CommandType.EXIT) {
                            executor.shutdownNow();

                           // executor.shutdown();
                        }
                        outputStream.close();
                        inputStream.close();
                        socket.close();


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                if (executor.isShutdown()) {
                    serverSocket.close();
                    break;
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }*/
