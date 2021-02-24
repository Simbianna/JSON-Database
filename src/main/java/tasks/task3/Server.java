package tasks.task3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server {
    private String address;
    private int port;

    private List<String> database = Stream.generate(String::new)
            .limit(1000)
            .collect(Collectors.toList());

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String performAndGetResponse(String msg) {
        String result;

        if (msg == null || msg.isEmpty() || msg.isBlank()) {
            result = getMessage(Status.ERROR, "");
        } else {
            String[] params = msg.split(" ", 3);
            if (params[0].toLowerCase().equals("exit")) {
                result = getMessage(Status.EXIT, null);
                return result;
            } else {
                String action = params[0];
                switch (action) {
                    case "set":
                        result = set(Integer.parseInt(params[1]), params[2]);
                        break;
                    case "get":
                        result = get(Integer.parseInt(params[1]));
                        break;
                    case "delete":
                        result = delete(Integer.parseInt(params[1]));
                        break;
                    default:
                        result = "ERROR";
                }
            }
        }
        return result;
    }


    private String set(int position, String data) {
        if (checkPositionInRange(position)) {
            database.add(position - 1, data);
            return getMessage(Status.OK, data);
        } else return getMessage(Status.ERROR, "");
    }

    private String get(int position) {
        if (checkPositionInRange(position)) {
            String result = database.get(position - 1);
            if (result != null && !result.isBlank() && !result.isEmpty()) {
                return getMessage(Status.OK_WITH_MESSAGE, result);
            } else return getMessage(Status.ERROR, "");
        } else return getMessage(Status.ERROR, "");
    }

    private String delete(int position) {
        if (!checkPositionInRange(position)) {
            System.out.println("not in range " + position);
            return getMessage(Status.ERROR, "");
        } else {
            String result = database.get(position - 1);
            if (result != null && !result.isBlank() && !result.isEmpty()) {
                database.remove(position - 1);
            }
            return getMessage(Status.OK, "");
        }
    }

    private static boolean checkPositionInRange(int position) {
        return position >= 1 && position <= 1000;
    }

    private static String getMessage(Status status, String message) {
        String response;
        if (status == Status.OK) {
            response = "OK";
        } else if (status == Status.ERROR) {
            response = "ERROR";
        } else if (status == Status.EXIT) {
            response = "EXIT";
        } else response = message;
        return response;
    }

    public static void printData(String data) {
        System.out.println(data);
    }
}
