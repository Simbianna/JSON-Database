package tasks.task6.pack.command;

import tasks.task6.pack.db.JsonDbFile;
import tasks.task6.pack.util.RequestObject;

public class DeleteDataCommand implements DatabaseCommand {
    private JsonDbFile database;
    RequestObject object;

    public DeleteDataCommand(JsonDbFile database, RequestObject object) {
        this.database = database;
        this.object = object;
    }

    @Override
    public String execute() {
        return database.delete(object);
    }
}
