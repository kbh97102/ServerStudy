package chatting.core;

import chatting.communication.Server_gui;

public class ServerMain {
    public static void main(String[] args) {
        Server_gui server = new Server_gui();
        server.run();
    }
}
