package tasks.task6.pack.command;

import tasks.task6.pack.db.JsonDbFile;
import tasks.task6.pack.util.RequestObject;

public class SetDataCommand implements DatabaseCommand {
    private JsonDbFile database;
    RequestObject object;

    public SetDataCommand(JsonDbFile database, RequestObject object) {
        this.database = database;
        this.object = object;
    }

    @Override
    public String execute() {
        return database.set(object);
    }
}
