package tasks.task3.command;

import tasks.task3.db.Database;

public class SetDataCommand implements DatabaseCommand {

    private Database database;
    private int position;
    private String data;

    public SetDataCommand(Database database, int position, String data) {
        this.database = database;
        this.position = position;
        this.data = data;
    }

    @Override
    public String execute() {
        return database.set(position, data);
    }
}
