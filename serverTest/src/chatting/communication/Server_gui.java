package chatting.communication;

import chatting.core.Attachment;
import chatting.hadler.forServer.AcceptHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;

public class Server_gui implements Runnable{

    private AsynchronousServerSocketChannel serverSocket;
    private AsynchronousChannelGroup group;

    public Server_gui(){
        try {
            group = AsynchronousChannelGroup.withFixedThreadPool(5, Executors.defaultThreadFactory());
            serverSocket = AsynchronousServerSocketChannel.open(group);
            serverSocket.bind(new InetSocketAddress("127.0.0.1",3000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        Attachment att = new Attachment();
        att.setServer(serverSocket);
        serverSocket.accept(att,new AcceptHandler());

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
