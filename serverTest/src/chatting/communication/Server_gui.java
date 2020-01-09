package chatting.communication;

import chatting.core.Attachment;
import chatting.hadler.forServer.TestAcceptHandler;
import chatting.hadler.forServer.TestReadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Server_gui{

    private AsynchronousServerSocketChannel serverSocket;
    private AsynchronousChannelGroup group;
    private Vector<Attachment> clients = new Vector<>();
    private Consumer<String> display;

    public Server_gui(Consumer<String> display){
        this.display = display;
        try {
            group = AsynchronousChannelGroup.withFixedThreadPool(5, Executors.defaultThreadFactory());
            serverSocket = AsynchronousServerSocketChannel.open(group);
            serverSocket.bind(new InetSocketAddress("192.168.200.104",3000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAccept() {
        Attachment att = new Attachment();
        att.setServer(serverSocket);
        att.setClients(clients);
        serverSocket.accept(att,new TestAcceptHandler(display, this::readFromClient));
    }

    public void readFromClient(){
        for (Attachment client : clients) {
            if (!client.isReadMode()) {
                System.out.println("Read test");
                client.setReadMode(true);
                client.getClient().read(client.getBuffer(), client, new TestReadHandler(display));
            }
        }
    }

    public void sentToAllClient(String msg){
        Charset charset = StandardCharsets.UTF_8;
        Iterator<Attachment> clientIterator = clients.iterator();
        ByteBuffer buffer = charset.encode(msg);
        while(clientIterator.hasNext()){
            Attachment att = clientIterator.next();
            att.getClient().write(buffer);
        }
    }

}
