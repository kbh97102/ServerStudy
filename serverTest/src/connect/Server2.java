package connect;

import thread.AcceptTask;
import thread.ReadTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Server2 {

    private static Vector<Client1> connections = new Vector<>();
    private static Selector selector;
    private static ServerSocketChannel serverSocket;

    public static void main(String[] args) {
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("127.0.0.1", 3000));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AcceptTask acceptTask = new AcceptTask(serverSocket,selector,connections);
        ReadTask readTask = new ReadTask(serverSocket,selector,connections);

        acceptTask.run();

    }
}
