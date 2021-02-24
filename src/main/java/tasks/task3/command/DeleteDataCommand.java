package tasks.task3.command;

import tasks.task3.db.Database;

public class DeleteDataCommand implements DatabaseCommand {
    private Database database;
    private int position;

    public DeleteDataCommand(Database database, int position) {
        this.database = database;
        this.position = position;
    }

    @Override
    public String execute() {
       return database.delete(position);
    }
}
