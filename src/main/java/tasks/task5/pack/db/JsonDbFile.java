package tasks.task5.pack.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tasks.task5.pack.db.dbResponse.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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
   private static ReadWriteLock lock = new ReentrantReadWriteLock();
   private static Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();

    public JsonDbFile(String fileName) {
        if (fileName != null) {
            file = new File(fileName);
        } else file = new File(defaultPath);
    }

    public String set(String key, String value) {
        DbResponse dBResponse;
        Set<DbEntity> fileData = getEntitiesSetFromFile(file.getAbsolutePath());
        DbEntity entity = checkEntityExists(fileData, key);
        if (entity != null){
            fileData.remove(entity);
        }
        fileData.add(new DbEntity(key, value));
        updateFile(getJsonStringFromEntitiesSet(fileData));
        dBResponse = new OKEmptyResponse();

        return getJsonFromObject(dBResponse);
    }

    public String get(String key) {
        DbResponse dBResponse;
        Set<DbEntity> fileData = getEntitiesSetFromFile(file.getAbsolutePath());
        DbEntity entity = checkEntityExists(fileData, key);
        if (entity != null) {
            String value = entity.getValue();
            dBResponse = new OKValueResponse(value);
        } else dBResponse = new ErrorResponse("No such key");
        return getJsonFromObject(dBResponse);
    }

    public String delete(String key) {
        DbResponse dBResponse;
        Set<DbEntity> fileData = getEntitiesSetFromFile(file.getAbsolutePath());
        DbEntity entity = checkEntityExists(fileData, key);

        if (entity != null) {
            fileData.remove(entity);
            updateFile(getJsonStringFromEntitiesSet(fileData));
            dBResponse = new OKEmptyResponse();
        } else dBResponse = new ErrorResponse("No such key");
        return getJsonFromObject(dBResponse);
    }

    public String exit() {
        return getJsonFromObject(new OKEmptyResponse());
    }

    private static Set<DbEntity> getEntitiesSetFromFile(String filename) {
        Gson gson = new Gson();
        String data = readFileToString(filename);
        Type founderListType = new TypeToken<HashSet<DbEntity>>() {
        }.getType();

        return gson.fromJson(data, founderListType);
    }

    public String getError(String errorMsg) {
        return getJsonFromObject(new ErrorResponse(errorMsg));
    }

    private static String   readFileToString(String filename) {
        readLock.lock();
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            readLock.unlock();
        }
        return result;
    }

    private void updateFile(String fullData) {
        writeLock.lock();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(fullData);
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    private static String getJsonStringFromEntitiesSet(Set<DbEntity> entitySet) {
        Gson gson = new Gson();
        return gson.toJson(entitySet);
    }


    private static DbEntity checkEntityExists(Set<DbEntity> entitySet, String key) {
        DbEntity result = null;

        for (DbEntity entity : entitySet) {
            if (entity.getKey().equals(key)) {
                result = entity;
                break;
            }
        }
        return result;
    }
}
