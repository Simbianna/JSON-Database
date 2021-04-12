package tasks.task6.pack.command;

import tasks.task6.pack.db.JsonDbFile;

public class ErrorDataCommand implements DatabaseCommand {
    private JsonDbFile database;
    private String errorMsg;

    public ErrorDataCommand(JsonDbFile database, String errorMsg) {
        this.database = database;
        this.errorMsg = errorMsg;
    }

    @Override
    public String execute() {
        return database.getError(errorMsg);
    }
}
