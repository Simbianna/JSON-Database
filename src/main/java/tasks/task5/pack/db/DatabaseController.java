package tasks.task5.pack.db;

import tasks.task5.pack.command.DatabaseCommand;

public class DatabaseController {
    private DatabaseCommand command;

    public void setCommand(DatabaseCommand command) {
        this.command = command;
    }

    public String executeCommand() {
        return command.execute();
    }
}
