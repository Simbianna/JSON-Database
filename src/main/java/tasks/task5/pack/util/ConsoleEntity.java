package tasks.task5.pack.util;


import tasks.task5.pack.command.CommandType;

public class ConsoleEntity {
    private CommandType type;
    private String key;
    private String value;

    public ConsoleEntity(CommandType type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public ConsoleEntity(CommandType type, String key) {
        this.type = type;
        this.key = key;
    }

    public ConsoleEntity(CommandType type) {
        this.type = type;
    }

    public static ConsoleEntity getErrorConsoleEntity(String json){
        return new ConsoleEntity(CommandType.ERROR, json);
    }

    public static ConsoleEntity getUnknownConsoleEntity(){
        return new ConsoleEntity(CommandType.UNKNOWN);
    }

    public CommandType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
