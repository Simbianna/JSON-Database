package tasks.task3.util;


public class ResponseMessageCreator {

    public static String getResponseMessage(ResponseStatus status, String message) {
        String response;

        if (status == ResponseStatus.OK) {
            response = "OK";
        } else if (status == ResponseStatus.ERROR) {
            response = "ERROR";
        } else if (status == ResponseStatus.EXIT) {
            response = "EXIT";
        } else response = message;
        return response;
    }
}
