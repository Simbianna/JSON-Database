package tasks.task4.pack.db;

import tasks.task4.pack.command.DatabaseCommand;

public class DatabaseController {
    private DatabaseCommand command;

    public void setCommand(DatabaseCommand command) {
        this.command = command;
    }

    public String executeCommand() {
        return command.execute();
    }
}
