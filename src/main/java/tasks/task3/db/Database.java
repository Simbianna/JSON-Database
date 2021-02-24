package tasks.task3.db;

import tasks.task3.util.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static tasks.task3.util.ResponseMessageCreator.getResponseMessage;

public class Database {
    private List<String> lines;
    private int size;

    public Database(int size) {
        this.size = size;
        initDB(size);
    }

    private void initDB(int size) {
        lines = Stream.generate(String::new)
                .limit(size)
                .collect(Collectors.toList());
    }

    public String set(int position, String data) {
        if (checkPositionInRange(position)) {
            lines.add(position - 1, data);
            return getResponseMessage(ResponseStatus.OK, data);
        } else return getResponseMessage(ResponseStatus.ERROR, "");
    }

    public String get(int position) {
        if (checkPositionInRange(position)) {
            String result = lines.get(position - 1);
            if (result != null && !result.isBlank() && !result.isEmpty()) {
                return getResponseMessage(ResponseStatus.OK_WITH_MESSAGE, result);
            } else return getResponseMessage(ResponseStatus.ERROR, "");
        } else return getResponseMessage(ResponseStatus.ERROR, "");
    }

    public String delete(int position) {
        if (!checkPositionInRange(position)) {
            System.out.println("not in range " + position);
            return getResponseMessage(ResponseStatus.ERROR, "");
        } else {
            String result = lines.get(position - 1);
            if (result != null && !result.isBlank() && !result.isEmpty()) {
                lines.remove(position - 1);
            }
            return getResponseMessage(ResponseStatus.OK, "");
        }
    }



    private boolean checkPositionInRange(int position) {
        return position >= 1 && position <= size;
    }
}
