package tasks.task6.pack.db;

import com.google.gson.*;

import tasks.task6.pack.db.dbResponse.*;
import tasks.task6.pack.util.RequestObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static tasks.task4.pack.util.MessageProcessor.getJsonFromObject;

public class JsonDbFile {
    private File file;
    private final String defaultPath = "/data/db.json";
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public JsonDbFile(String fileName) {
        if (fileName != null) {
            file = new File(fileName);
        } else file = new File(defaultPath);
    }

    private void updateElement(JsonObject objectToUpdate, List<String> levels, JsonElement value) {
        JsonElement element;

        for (int i = 0; i < levels.size(); i++) {
            element = objectToUpdate.get(levels.get(i));
            if (i == levels.size() - 1 || element == null) {
                objectToUpdate.add(levels.get(0), value);
                break;
            }
        }
    }

    private JsonElement findElement(JsonArray jsonArray, List<String> levels) {
        JsonElement result = JsonNull.INSTANCE;
        JsonElement innerElement;
        JsonObject object;

        for (JsonElement element : jsonArray) {
            object = element.getAsJsonObject();

            for (int i = 0; i < levels.size(); i++) {
                innerElement = object.get(levels.get(i));
                if (innerElement == null) {
                    break;
                } else if (i == levels.size() - 1) {
                    result = innerElement;
                }
            }
        }
        return result;
    }

    private JsonArray removeElement(JsonArray jsonArray, List<String> levels) {
        JsonArray copyArray = jsonArray.deepCopy();
        JsonObject object;
        JsonElement innerElement;
        boolean result = false;

        for (JsonElement element : jsonArray) {

            object = element.getAsJsonObject();
            for (int i = 0; i < levels.size(); i++) {
                innerElement = object.get(levels.get(i));
                if (innerElement == null) {
                    break;
                } else if (i == levels.size() - 1) {
                    object.remove(levels.get(i));
                    copyArray.remove(element);
                    jsonArray.add(innerElement);
                    result = true;
                }
            }
            if (result) break;
        }
        return result ? copyArray : null;
    }

    public String set(RequestObject request) {
        DbResponse dBResponse;
        JsonArray jsonArray = readFileToJsonArray(file.getAbsolutePath());

        for (JsonElement element : jsonArray) {
            if (element.getAsJsonObject().has(getKeyPath(request.getKey()).get(0))) {
                updateElement(element.getAsJsonObject(), getKeyPath(request.getKey()), request.getValue());
                break;
            }
        }

        writeJsonArrayToFile(file.getAbsolutePath(), jsonArray);
        dBResponse = new OKEmptyResponse();
        return getJsonFromObject(dBResponse);
    }

    public String get(RequestObject request) {
        DbResponse dBResponse;
        JsonArray jsonArray = readFileToJsonArray(file.getAbsolutePath());
        JsonElement result = findElement(jsonArray, getKeyPath(request.getKey()));

        if (result instanceof JsonNull) dBResponse = new ErrorResponse("No such key");
        else {
            String value = result.toString();
            dBResponse = new OKValueResponse(value);
        }
        return getJsonFromObject(dBResponse);
    }

    public String delete(RequestObject request) {
        DbResponse dBResponse;
        JsonArray jsonArray = readFileToJsonArray(file.getAbsolutePath());
        JsonArray updatedArray = removeElement(jsonArray, getKeyPath(request.getKey()));

        if (updatedArray == null) {
            dBResponse = new ErrorResponse("No such key");
        } else {
            writeJsonArrayToFile(file.getAbsolutePath(), updatedArray);
            dBResponse = new OKEmptyResponse();
        }

        return getJsonFromObject(dBResponse);

    }

    public String exit() {
        return getJsonFromObject(new OKEmptyResponse());
    }


    public String getError(String errorMsg) {
        return getJsonFromObject(new ErrorResponse(errorMsg));
    }

    private JsonArray readFileToJsonArray(String filename) {
        readLock.lock();
        JsonArray result = new JsonArray();

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filename)));
            JsonParser parser = new JsonParser();
            result = parser.parse(fileContent).getAsJsonArray();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return result;
    }

    private void writeJsonArrayToFile(String filename, JsonArray array) {
        writeLock.lock();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(array.toString());
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            writeLock.unlock();
        }

    }

    private List<String> getKeyPath(String key){
        List<String> result;
        if (key.endsWith("]")){
            result = Arrays.asList(key
                    .replaceAll("\"", "" )
                    .replaceAll("]", "")
                    .replaceAll("\\[", "")
                    .split(" "));
        }
        else result = Arrays.asList(key.split(" "));
        return result;
    }
}
