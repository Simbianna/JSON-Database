package tasks.task5.server;

public class ServerStart {

    public static void main(String[] args) {
        Server server = Server.createDefaultServer();
        server.run();
    }

}
