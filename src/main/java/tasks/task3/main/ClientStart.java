package tasks.task3.main;

import com.beust.jcommander.JCommander;
import tasks.task3.util.MainArgs;


public class ClientStart {

    public static void main(String[] args) {
        MainArgs mainArgs = new MainArgs();

        JCommander.newBuilder()
                .addObject(mainArgs)
                .build()
                .parse(args);

        Client client = Client.getDefaultClient();
        client.run(mainArgs);
    }
}
