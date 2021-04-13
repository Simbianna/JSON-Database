package tasks.task4.pack.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import tasks.task4.pack.command.CommandType;
import tasks.task6.pack.db.dbResponse.DbResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MessageProcessor {

    public static ConsoleEntity getConsoleEntityFromJson(String json) {
        ConsoleEntity result = ConsoleEntity.getUnknownConsoleEntity();
        Gson gson = new Gson();

        if (json != null && json.length() > 0) {
            try {
                ArgsEntity args = gson.fromJson(json, ArgsEntity.class);
                String command = args.getCommand();
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
