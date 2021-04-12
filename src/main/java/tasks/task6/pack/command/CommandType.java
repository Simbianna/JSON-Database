package tasks.task6.pack.command;

@Deprecated
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
