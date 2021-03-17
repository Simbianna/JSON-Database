package tasks.task4.pack.db;


import tasks.task4.pack.db.dbResponse.DbResponse;
import tasks.task4.pack.db.dbResponse.ErrorResponse;
import tasks.task4.pack.db.dbResponse.OKEmptyResponse;
import tasks.task4.pack.db.dbResponse.OKValueResponse;

import java.util.HashMap;
import java.util.Map;

import static tasks.task4.pack.util.MessageProcessor.getJsonFromObject;


public class JsonDb {
    private Map<String, String> database;
    private int maxSize;

    public JsonDb(int maxSize) {
        initDB();
        this.maxSize = maxSize;
    }

    private void initDB() {
        database = new HashMap<>();
    }

    public String set(String key, String value) {
        DbResponse dBResponse;
        if (database.containsKey(key) || checkSlotsAvailable()) {
            database.put(key, value);
            dBResponse = new OKEmptyResponse();
        } else dBResponse = new ErrorResponse("Database id full");
        return getJsonFromObject(dBResponse);
    }

    public String get(String key) {
        DbResponse dBResponse;

        if (database.containsKey(key)) {
            String value = database.get(key);
            dBResponse = new OKValueResponse(value);
        } else dBResponse = new ErrorResponse("No such key");
        return getJsonFromObject(dBResponse);
    }

    public String delete(String key) {
        DbResponse dBResponse;

        if (database.containsKey(key)) {
            database.remove(key);
            dBResponse = new OKEmptyResponse();
        } else dBResponse = new ErrorResponse("No such key");
        return getJsonFromObject(dBResponse);
    }

    public String exit(){
        return getJsonFromObject(new OKEmptyResponse());
    }

    public String getError(String errorMsg) {
        return getJsonFromObject(new ErrorResponse(errorMsg));
    }

    public int getMaxSize() {
        return maxSize;
    }

    private boolean checkSlotsAvailable() {
        return maxSize - database.size() > 0;
    }

}


