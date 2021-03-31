package tasks.task5.pack.util;

import com.beust.jcommander.Parameter;

public class    ArgsEntity {
    @Parameter(names = "-in")
    private String file;

    @Parameter(names = "-t")
    private String type;

    @Parameter(names = "-k")
    private String key;

    @Parameter(names = "-v")
    private String value;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFile() {
        return file;
    }
}

