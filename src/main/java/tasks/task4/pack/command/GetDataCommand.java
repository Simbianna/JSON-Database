package tasks.task4.pack.command;

import tasks.task4.pack.db.JsonDb;

public class GetDataCommand implements DatabaseCommand {
    private JsonDb database;
    private String  key;

    public GetDataCommand(JsonDb database, String key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public String execute() { return database.get(key);
    }
}
