package chatting.communication;

import chatting.core.Attachment;
import chatting.hadler.forServer.ReadImageHandler;
import chatting.hadler.forServer.TestAcceptHandler;
import chatting.hadler.forServer.TestReadHandler;

import javax.swing.*;
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
    private Consumer<ImageIcon> imageDisplay;

    public Server_gui(Consumer<String> display, Consumer<ImageIcon> imageDisplay){
        this.imageDisplay = imageDisplay;
        this.display = display;
        try {
            group = AsynchronousChannelGroup.withFixedThreadPool(5, Executors.defaultThreadFactory());
            serverSocket = AsynchronousServerSocketChannel.open(group);
            serverSocket.bind(new InetSocketAddress("127.0.0.1",3000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAccept() {
        Attachment att = new Attachment();
        att.setServer(serverSocket);
        att.setClients(clients);
//        serverSocket.accept(att,new TestAcceptHandler(display, this::readFromClient));
        serverSocket.accept(att,new TestAcceptHandler(display, this::readDisplayImage));
    }

    public void readFromClient(){
        for (Attachment client : clients) {
            if (!client.isReadMode()) {
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

    public void readDisplayImage(){
        for (Attachment client : clients) {
            if (!client.isReadMode()) {
                client.setReadMode(true);
                client.setBuffer(ByteBuffer.allocate(100000));
                client.getClient().read(client.getBuffer(), client, new ReadImageHandler(imageDisplay));
            }
        }

    }

}
