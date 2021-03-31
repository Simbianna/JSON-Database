package tasks.task5.pack.db.dbResponse;

public class OKEmptyResponse implements DbResponse {
    private final String response = "OK";

    public OKEmptyResponse() {
    }

    public String getResponse() {
        return response;
    }
}
