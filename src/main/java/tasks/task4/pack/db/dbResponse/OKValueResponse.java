package tasks.task4.pack.db.dbResponse;

public class OKValueResponse implements DbResponse {
    private final String response = "OK";
    private String value;

    public OKValueResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getResponse() {
        return response;
    }
}
