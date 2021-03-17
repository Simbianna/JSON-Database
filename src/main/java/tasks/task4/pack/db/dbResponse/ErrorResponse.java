package tasks.task4.pack.db.dbResponse;

public class ErrorResponse implements DbResponse {
    private final String response = "ERROR";
    private String reason;

    public ErrorResponse(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String getResponse() {
        return response;
    }

}
