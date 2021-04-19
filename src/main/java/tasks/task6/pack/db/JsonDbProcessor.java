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

}
