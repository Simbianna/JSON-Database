package tasks.task4.pack.command;

public enum CommandType {
    SET("set"),
    GET("get"),
    DELETE("delete"),
    ERROR("error"),
    EXIT("exit"),
    UNKNOWN("unknown");


    public final String val;

    CommandType(String val) {
        this.val = val;
    }
}
