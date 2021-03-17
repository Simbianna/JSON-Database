package tasks.task4.pack.util;

import com.beust.jcommander.Parameter;

public class ArgsEntity {
    @Parameter(names = "-t")
    private String command;

    @Parameter(names = "-k")
    private String key;

    @Parameter(names = "-v")
    private String value;

    public String getCommand() {
        return command;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


}

