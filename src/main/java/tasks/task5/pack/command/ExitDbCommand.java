package tasks.task5.pack.command;

import tasks.task5.pack.db.JsonDbFile;

public class ExitDbCommand implements DatabaseCommand {
    private JsonDbFile database;

    public ExitDbCommand(JsonDbFile database) {
        this.database = database;
    }

    @Override
    public String execute() {
        return database.exit();
    }
}
