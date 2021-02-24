package tasks.task3.db;

import tasks.task3.command.DatabaseCommand;

public class DatabaseController {
    private DatabaseCommand command;

    public void setCommand(DatabaseCommand command) {
        this.command = command;
    }

    public String executeCommand() {
        return command.execute();
    }
}
