package tasks.task3.util;

import com.beust.jcommander.Parameter;

public class MainArgs {
    @Parameter(names = "-t")
    private String command;

    @Parameter(names = "-i")
    private int position;

    @Parameter(names = "-m")
    private String msg;

    public String getCommand() {
        return command;
    }

    public int getPosition() {
        return position;
    }

    public String getMsg() {
        return msg;
    }

    public String getArgsAsString() {
        if (command.equals("get") ||
                command.equals("delete"))
            return  String.format("%s %d", command, position);
        else if (command.equals("set")) return String.format("%s %d %s", command, position, msg);
        else return command;
    }

}

