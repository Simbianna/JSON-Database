package tasks.task6.pack.util;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MessageProcessor {
    private static final String clientFilesPath = System.getProperty("user.dir") + "/src/main/java/tasks/task6/client/data/";

    public static RequestObject getRoFromReceivedData(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, RequestObject.class);
    }

    public static String getJsonFromArgsEntity(ArgsEntity argsEntity) {
        Gson gson = new Gson();
        String result;

        if (argsEntity.getFile() != null) {
            try {
                Path path = Paths.get(clientFilesPath + argsEntity.getFile());
                byte[] buff = Files.readAllBytes(path);
                result = new String(buff);
            } catch (IOException ioException) {
                return "";
            }
        } else result = gson.toJson(argsEntity);
        return result;
    }

    public static String getJsonFromObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object)
                .replace("\\\"", "\"")
                .replace("}\"}", "}}")
                .replace("\"\"", "\"")
                .replace("\":\"{", "\":{");
    }
}
