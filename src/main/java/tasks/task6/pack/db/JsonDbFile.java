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

    private void updateElement(JsonArray jsonArray, List<String> levels, JsonElement value) {
        JsonObject object;
        boolean added = false;

        if (jsonArray != null) {

            for (int i = 0; i < jsonArray.size(); i++) {
                object = jsonArray.get(i).getAsJsonObject();
                if (object.has(levels.get(0))) {
                    if (levels.size() == 1) {
                        object.add(levels.get(0), value);
                        added = true;
                        break;
                    }
               //     object = object.get(levels.get(1)).getAsJsonObject();
                    for (int j = 1; j < levels.size(); j++) {

                        if (object.has(levels.get(j))) {
                            object = object.getAsJsonObject(levels.get(j));
                        } else {

                            for (int k = j; k < levels.size(); k++) {
                                if (j == levels.size() - 1) {
                                    object.add(levels.get(j), value);
                                    added = true;
                                } else if (object.has(levels.get(j))) {
                                    object = object.get(levels.get(i)).getAsJsonObject();
                                } else {
                                    object.add(levels.get(j), new JsonObject());
                                    object = object.get(levels.get(j)).getAsJsonObject();
                                }

                            }

                        }
                    }
                }
                if (added) break;
            }
            if (!added) {
                addNewElement(jsonArray, levels, value);
            }
        }

    }

    private void addNewElement(JsonArray jsonArray, List<String> levels, JsonElement element) {
        JsonObject object = new JsonObject();

        for (int i = 0; i < levels.size(); i++) {
            if (i == levels.size() - 1) {
                object.add(levels.get(i), element);
            } else object.add(levels.get(i), new JsonObject());
        }
        jsonArray.add(object);
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
                } else object = innerElement.getAsJsonObject();
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
            String value = result.toString().replace("\\", "");
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

    private List<String> getKeyPath(JsonElement key) {
        String line = key.toString();
        List<String> result;

        result = Arrays.asList(line
                .replaceAll("[\"\\[\\]]", "").split(","));
            /*result = Arrays.asList(line
                    .replaceAll("\"", "")
                    .replaceAll("]", "")
                    .replaceAll("\\[", "")
                    .split(" "));*/
        return result;
    }
}
