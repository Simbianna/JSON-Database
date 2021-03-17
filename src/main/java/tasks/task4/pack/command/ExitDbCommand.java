package tasks.task4.pack.command;

import tasks.task4.pack.db.JsonDb;

public class ExitDbCommand implements DatabaseCommand {
    private JsonDb database;

    public ExitDbCommand(JsonDb database) {
        this.database = database;
    }

    @Override
    public String execute() {
        return database.exit();
    }
}
