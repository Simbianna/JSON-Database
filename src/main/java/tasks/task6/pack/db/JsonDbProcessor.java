package tasks.task6.pack.db;


import tasks.task6.pack.command.*;
import tasks.task6.pack.util.RequestObject;


import java.util.Objects;

public class JsonDbProcessor {

    public static String processRequestObject(RequestObject object, JsonDbFile database) {
        String result;
        DatabaseController controller = new DatabaseController();

        if (object != null) {
            String command = Objects.requireNonNullElse(object.getType(), ".").toUpperCase();

            switch (command) {
                case "SET": {
                    controller.setCommand(new SetDataCommand(database, object));
                    break;
                }
                case "GET": {
                    controller.setCommand(new GetDataCommand(database, object));
                    break;
                }
                case "DELETE": {
                    controller.setCommand(new DeleteDataCommand(database, object));
                    break;
                }

                case "EXIT": {
                    controller.setCommand(new ExitDbCommand(database));
                    break;
                }
                default: {
                    controller.setCommand(new ErrorDataCommand(database, "unknown command"));
                }
            }
        }
        result = controller.executeCommand();
        return result;
    }

/*    public static String processJsonElement(JsonElement element, JsonDbFile database) {
        String result;
        DatabaseController controller = new DatabaseController();

        if (element instanceof JsonNull) {
            controller.setCommand(new ErrorDataCommand(database, "unknown command"));
        }
        else {
            JsonObject object = element.getAsJsonObject();
            String command = Objects
                    .requireNonNullElse(object.get("type").getAsString(), ".")
                    .toUpperCase();
            String key = object.get("key").getAsString();

            JsonObject inner = object.getAsJsonObject("value");

            object.remove("type");
            object.remove("value");
            object.add(key, inner);

            switch (command) {
                case "SET": {
                    controller.setCommand(new SetDataCommand(database, object, key));
                    break;
                }
                case "GET": {
                    controller.setCommand(new GetDataCommand(database, object, key));
                    break;
                }
                case "DELETE": {
                    controller.setCommand(new DeleteDataCommand(database, object, key));
                    break;
                }

                case "EXIT": {
                    controller.setCommand(new ExitDbCommand(database));
                    break;
                }
                default: {
                    controller.setCommand(new ErrorDataCommand(database, "unknown command"));
                }
            }
        }
        result = controller.executeCommand();
        return result;
    }*/


    /*public static String processConsoleEntity(ConsoleEntity data, JsonDbFile database) {
        String result;
        DatabaseController controller = new DatabaseController();
        CommandType command = data.getType();

        switch (command) {
            case SET: {
                controller.setCommand(new SetDataCommand(database, data.getKey(), data.getValue()));
                break;
            }
            case GET: {
                controller.setCommand(new GetDataCommand(database, data.getKey()));
                break;
            }
            case DELETE: {
                controller.setCommand(new DeleteDataCommand(database, data.getKey()));
                break;
            }

            case EXIT: {
                controller.setCommand(new ExitDbCommand(database));
                break;
            }
            default: {
                controller.setCommand(new ErrorDataCommand(database, "unknown command"));
            }
        }
        result = controller.executeCommand();

        return result;
    }*/
}
