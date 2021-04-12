package tasks.task6.server;


public class ServerStarter {

    public static void main(String[] args) {
        Server server = Server.createDefaultServer();
        server.run();
    }

}
