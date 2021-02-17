package tasks;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task1 {

    // private static List<String> database = new ArrayList<>(100);

    private static List<String> database = Stream.generate(String::new)
            .limit(100)
            .collect(Collectors.toList());

    public static void main(String[] args) {
        // System.out.println("Hello, world!");
        //    Collections.fill(database, "null");
        try (Scanner scanner = new Scanner(System.in)) {
            String line;
            line = scanner.nextLine();
            while (!line.equals("exit")) {
                chooseAction(line);
                line = scanner.nextLine();
            }
        }
    }

    private static void chooseAction(String line) {
        if (line == null || line.isEmpty() || line.isBlank()) {
            printResult(false);
        } else {
            String[] params = line.split(" +", 3);
            if (params.length < 2
                    || !params[1].matches("[0-9]{1,3}")) {
                printResult(false);
            } else {
                int position = Integer.parseInt(params[1]);
                String action = params[0];
                String data = "";
                if (params.length > 2) {
                    data = params[2];
                }
                switch (action) {
                    case "set":
                        set(position, data);
                        break;
                    case "get":
                        get(position);
                        break;
                    case "delete":
                        delete(position);
                }
            }
        }
    }

    private static void set(int position, String data) {
        if (checkPositionInRange(position)) {
            database.add(position - 1, data);
            printResult(true);
        } else printResult(false);
    }

    private static void get(int position) {
        if (checkPositionInRange(position)) {
            String result = database.get(position - 1);
            if (result != null && !result.isBlank() && !result.isEmpty()) {
                printData(result);
            } else printResult(false);
        } else printResult(false);
    }

    private static void delete(int position) {
        if (!checkPositionInRange(position)) {
            printResult(false);
        } else {
            String result = database.get(position - 1);
            if (result != null && !result.isBlank() && !result.isEmpty()) {
                database.remove(position - 1);
            }
            printResult(true);
        }
    }

    private static boolean checkPositionInRange(int position) {
        return position >= 1 && position <= 100;
    }

    private static void printResult(boolean isOk) {
        if (isOk) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
        }
    }

    private static void printData(String data) {
        System.out.println(data);
    }

}
