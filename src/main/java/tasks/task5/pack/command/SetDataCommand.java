package tasks.task5.pack.command;

import tasks.task5.pack.db.JsonDbFile;

public class SetDataCommand implements DatabaseCommand {

    private JsonDbFile database;
    private String key ;
    private String value;

    public SetDataCommand(JsonDbFile database, String key, String value) {
        this.database = database;
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        return database.set(key, value);
    }
}
