package tasks.task6.pack.util;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MessageProcessor {
    private static final String clientFilesPath = System.getProperty("user.dir") + "/src/main/java/tasks/task6/client/data/";

    public static RequestObject getRoFromReceivedData(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, RequestObject.class);
    }



    /* public static JsonElement getJsonElementFromReceivedData(String data) {
        if (data != null && data.length() > 0) {
            return new JsonParser().parse(data).getAsJsonObject();
        } else return JsonNull.INSTANCE;
    }*/

   /* public static ConsoleEntity getConsoleEntityFromJson(String json) {
        ConsoleEntity result = ConsoleEntity.getUnknownConsoleEntity();
        Gson gson = new Gson();

        if (json != null && json.length() > 0) {
            try {
                ArgsEntity args = gson.fromJson(json, ArgsEntity.class);
                String command = args.getType();
                if (Arrays.stream(CommandType.values()).map(v -> v.val).collect(Collectors.toList()).contains(command)) {
                    result.setType(CommandType.valueOf(command.toUpperCase()));
                    result.setKey(args.getKey());
                    result.setValue(args.getValue());
                }

            } catch (JsonSyntaxException jse) {
                System.out.println("wrong JSON format");
            }
        }
        return result;
    }*/

    public static String getJsonFromArgsEntity(ArgsEntity argsEntity) {
        Gson gson = new Gson();
        String result;

        if (argsEntity.getFile() != null) {
            try {
                Path path = Paths.get(clientFilesPath + argsEntity.getFile());
                byte[] buff = Files.readAllBytes(path);
                result = new String(buff);
            } catch (IOException ioException) {
                return "";
            }
        } else result = gson.toJson(argsEntity);
        return result;
    }

    public static String getJsonFromObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }


   /*public static ConsoleEntity getConsoleEntityFromMainArgs(MainArgs args) {
        ConsoleEntity entity;

        if (args.getCommand() == null) {
            entity = new ConsoleEntity(CommandType.ERROR);
        } else {
            String command = args.getCommand().toLowerCase();
            switch (command) {
                case "get": {
                    entity = new ConsoleEntity(CommandType.GET, args.getKey());
                    break;
                }
                case "delete": {
                    entity = new ConsoleEntity(CommandType.DELETE, args.getKey());
                    break;
                }
                case "set": {
                    entity = new ConsoleEntity(CommandType.SET, args.getKey(), args.getValue());
                    break;
                }
                case "exit": {
                    entity = new ConsoleEntity(CommandType.EXIT);
                }
                default: {
                    entity = new ConsoleEntity(CommandType.ERROR);
                }
            }
        }
        return entity;
    }*/

}
