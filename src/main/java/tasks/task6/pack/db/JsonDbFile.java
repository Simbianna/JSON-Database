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

import static tasks.task6.pack.util.MessageProcessor.getJsonFromObject;

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


    public String set(RequestObject request) {
        DbResponse dBResponse;
        JsonArray jsonArray = readFileToJsonArray(file.getAbsolutePath());

        updateElement(jsonArray, getKeyPath(request.getKey()), request.getValue());

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
        List<String> pathToElement = getKeyPath(request.getKey());
        boolean found = false;

        JsonObject object;
        JsonElement innerElement;

        for (int i = 0; i < jsonArray.size(); i++) {
            object = jsonArray.get(i).getAsJsonObject();
            if (found) break;

            for (int j = 0; j < pathToElement.size(); j++) {
                innerElement = object.get(pathToElement.get(j));
                if (innerElement == null) {
                    break;
                } else if (j == pathToElement.size() - 1) {
                    object.remove(pathToElement.get(j));
                    found = true;
                } else object = innerElement.getAsJsonObject();
            }
        }

        if (!found) {
            dBResponse = new ErrorResponse("No such key");
        } else {
            writeJsonArrayToFile(file.getAbsolutePath(), jsonArray);
            dBResponse = new OKEmptyResponse();
        }

        return getJsonFromObject(dBResponse);

    }

    public String exit() {
        return getJsonFromObject(new OKEmptyResponse());
    }

    private void updateElement(JsonArray jsonArray, List<String> pathToElement, JsonElement value) {
        JsonObject object;
        boolean added = false;

        if (jsonArray != null) {

            for (int i = 0; i < jsonArray.size(); i++) {
                object = jsonArray.get(i).getAsJsonObject();

                if (object.has(pathToElement.get(0))) {
                    if (pathToElement.size() == 1) {
                        object.add(pathToElement.get(0), value);
                        added = true;
                        break;
                    }
                    object = object.get(pathToElement.get(0)).getAsJsonObject();
                    for (int j = 1; j < pathToElement.size(); j++) {

                        if (object.has(pathToElement.get(j))) {
                            if (j == pathToElement.size() - 1) {
                                object.add(pathToElement.get(j), value);
                                added = true;
                                break;
                            } else
                                object = object.get(pathToElement.get(j)).getAsJsonObject();
                        } else {

                            for (int k = j; k < pathToElement.size(); k++) {
                                if (k == pathToElement.size() - 1) {
                                    object.add(pathToElement.get(k), value);
                                    added = true;
                                    break;
                                } else if (object.has(pathToElement.get(k))) {
                                    object = object.get(pathToElement.get(k)).getAsJsonObject();
                                } else {
                                    object.add(pathToElement.get(k), new JsonObject());
                                    object = object.get(pathToElement.get(k)).getAsJsonObject();
                                }

                            }

                        }
                    }
                }
                if (added) break;
            }
            if (!added) {
                addNewElement(jsonArray, pathToElement, value);
            }
        }

    }

    private void addNewElement(JsonArray jsonArray, List<String> pathToElement, JsonElement element) {
        JsonObject object = new JsonObject();

        for (int i = 0; i < pathToElement.size(); i++) {
            if (i == pathToElement.size() - 1) {
                object.add(pathToElement.get(i), element);
            } else object.add(pathToElement.get(i), new JsonObject());
        }
        jsonArray.add(object);
    }

    private JsonElement findElement(JsonArray jsonArray, List<String> pathToElement) {
        JsonElement result = JsonNull.INSTANCE;
        JsonElement innerElement;
        JsonObject object;

        for (JsonElement element : jsonArray) {
            object = element.getAsJsonObject();
            if (!(result instanceof JsonNull)) break;

            for (int i = 0; i < pathToElement.size(); i++) {
                innerElement = object.get(pathToElement.get(i));
                if (innerElement == null) {
                    break;
                } else if (i == pathToElement.size() - 1) {
                    result = innerElement;
                } else object = innerElement.getAsJsonObject();
            }
        }
        return result;
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

    private List<String> getKeyPath(JsonElement key) {
        String line = key.toString();
        List<String> result;

        result = Arrays.asList(line
                .replaceAll("[\"\\[\\]]", "").split(","));
        return result;
    }
}
