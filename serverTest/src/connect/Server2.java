package connect;

import thread.ServerProcess;

public class Server2 {

    public static void main(String[] args) {

        ServerProcess serverProcess = new ServerProcess();
        Thread process = new Thread(serverProcess);
        process.start();
    }
}
