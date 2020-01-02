package thread;

import connect.Client;
import connect.Client1;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Vector;

public class AcceptTask implements Runnable{

    private ServerSocketChannel serverSocket;
    private Selector selector;
    private Vector<Client1> connections;

    public AcceptTask(ServerSocketChannel serverSocket, Selector selector, Vector<Client1> connections){
        this.serverSocket = serverSocket;
        this.selector = selector;
        this.connections = connections;
    }
    @Override
    public void run() {
        while(true){
            try {
                System.out.println("연결 대기중");
                SocketChannel socket = serverSocket.accept();
                System.out.println("연결완료");
                socket.configureBlocking(false);
                Client1 client = new Client1(socket);
                socket.register(selector, SelectionKey.OP_READ);
                connections.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
