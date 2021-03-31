package tasks.task5.pack.command;

import tasks.task5.pack.db.JsonDbFile;

public class GetDataCommand implements DatabaseCommand {
    private JsonDbFile database;
    private String  key;

    public GetDataCommand(JsonDbFile database, String key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public String execute() { return database.get(key);
    }
}
