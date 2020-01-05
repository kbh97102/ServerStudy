package chatting.hadler.forServer;

import chatting.core.Attachment;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Vector;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Attachment> {

    private ServerHandler serverHandler = new ServerHandler();
    private Vector<AsynchronousSocketChannel> clients;

    public AcceptHandler(){
        clients = new Vector<>();
    }
    @Override
    public void completed(AsynchronousSocketChannel result, Attachment attachment) {
        clients.add(result);

        try {
            SocketAddress address = result.getRemoteAddress();
            System.out.println("This Address : "+address+" is connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachment.getServer().accept(attachment,this);

        Attachment forClient = new Attachment();
        forClient.setServer(attachment.getServer());
        forClient.setClient(result);
        forClient.setBuffer(ByteBuffer.allocate(1024));
        forClient.setReadMode(true);


        forClient.getClient().read(forClient.getBuffer(),forClient,new ServerHandler());
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {

    }
}
