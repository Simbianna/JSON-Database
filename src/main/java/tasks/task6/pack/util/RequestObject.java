package tasks.task6.pack.util;

import com.google.gson.JsonElement;


public class RequestObject {
    private String type;
    private JsonElement key;
    private JsonElement value;

    public RequestObject(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RequestObject{" +
                "type='" + type + '\'' +
                ", key=" + key +
                ", value=" + value +
                '}';
    }
}
