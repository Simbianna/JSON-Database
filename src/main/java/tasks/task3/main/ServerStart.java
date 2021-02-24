package tasks.task3.main;


public class ServerStart {

    public static void main(String[] args) {
        Server server = Server.createDefaultServer();
        server.run();
    }
}
