package tasks.task6.client;

import com.beust.jcommander.JCommander;
import tasks.task6.pack.util.ArgsEntity;


public class ClientStarter {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        ArgsEntity argsEntity = new ArgsEntity();
        JCommander.newBuilder()
                .addObject(argsEntity)
                .build()
                .parse(args);

        Client client = Client.getDefaultClient();
        client.run(argsEntity);
    }
}
