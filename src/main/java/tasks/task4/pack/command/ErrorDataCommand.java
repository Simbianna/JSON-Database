package tasks.task4.pack.command;

import tasks.task4.pack.db.JsonDb;

public class ErrorDataCommand implements DatabaseCommand{
    private JsonDb database;
    private String errorMsg;

    public ErrorDataCommand(JsonDb database, String errorMsg) {
        this.database = database;
        this.errorMsg = errorMsg;
    }

    @Override
    public String execute() {
        return database.getError(errorMsg);
    }
}
