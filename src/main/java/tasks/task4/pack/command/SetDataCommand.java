package tasks.task4.pack.command;

import tasks.task4.pack.db.JsonDb;

public class SetDataCommand implements DatabaseCommand {

    private JsonDb database;
    private String key ;
    private String value;

    public SetDataCommand(JsonDb database, String key, String value) {
        this.database = database;
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        return database.set(key, value);
    }
}
