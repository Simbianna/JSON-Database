package tasks.task5.pack.db;


import tasks.task5.pack.command.*;
import tasks.task5.pack.util.ConsoleEntity;

public class JsonDbProcessor {

    public JsonDbProcessor() {
    }

    public static String processConsoleEntity(ConsoleEntity data, JsonDbFile database) {
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
    }
}
