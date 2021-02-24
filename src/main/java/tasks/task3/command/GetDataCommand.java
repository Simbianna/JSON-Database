package tasks.task3.command;

import tasks.task3.db.Database;

public class GetDataCommand implements DatabaseCommand {
    private Database database;
    private int position;

    public GetDataCommand(Database database, int position) {
        this.database = database;
        this.position = position;
    }

    @Override
    public String execute() { return database.get(position);
    }
}
