package tasks.task3.main;

import tasks.task3.db.Database;
import tasks.task3.db.DatabaseController;
import tasks.task3.command.DeleteDataCommand;
import tasks.task3.command.GetDataCommand;
import tasks.task3.command.SetDataCommand;
import tasks.task3.util.ResponseStatus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static tasks.task3.util.ResponseMessageCreator.getResponseMessage;

public class Server {
    private String address;
    private int port;
    private Database database;

    public Server(String address, int port, Database database) {
        this.address = address;
        this.port = port;
        this.database = database;
    }

    public static Server createDefaultServer() {
        return new Server("127.0.0.1", 23456, new Database(1000));
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

    public void setDatabase(Database database) {
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
                    String response = processMessage(msg);

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

    private String processMessage(String msg) {
        String result;

        if (msg == null || msg.isEmpty() || msg.isBlank()) {
            result = getResponseMessage(ResponseStatus.ERROR, "");
        } else {
            String[] params = msg.split(" ", 3);
            if (params[0].toLowerCase().equals("exit")) {
                result = getResponseMessage(ResponseStatus.EXIT, null);
                return result;
            } else {
                DatabaseController controller = new DatabaseController();
                String action = params[0];
                switch (action) {
                    case "set":
                        controller.setCommand(new SetDataCommand(database, Integer.parseInt(params[1]), params[2]));
                        result = controller.executeCommand();
                        break;
                    case "get":
                        controller.setCommand(new GetDataCommand(database, Integer.parseInt(params[1])));
                        result = controller.executeCommand();
                        break;
                    case "delete":
                        controller.setCommand(new DeleteDataCommand(database, Integer.parseInt(params[1])));
                        result = controller.executeCommand();
                        break;
                    default:
                        result = "ERROR";
                }
            }
        }
        return result;
    }
}
