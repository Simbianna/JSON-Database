package tasks.task4.pack.command;


import tasks.task4.pack.db.JsonDb;

public class DeleteDataCommand implements DatabaseCommand {
    private JsonDb database;
    private String key;

    public DeleteDataCommand(JsonDb database, String key) {
        this.database = database;
        this.key = key;
    }

    @Override
    public String execute() {
        return database.delete(key);
    }
}
