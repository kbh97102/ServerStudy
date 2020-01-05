package chatting.communication;

import chatting.hadler.forClient.ClientHandler;
import chatting.hadler.forClient.ClientReadHandler;
import result.Attachment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Client_gui {

    private Consumer<String> display;
    private ClientHandler clientHandler;
    private Charset charset = StandardCharsets.UTF_8;
    private Attachment attachment = new Attachment();
    private ClientReadHandler readHandler = new ClientReadHandler();

    public Client_gui(Consumer<String> display){
        this.display = display;
        clientHandler = new ClientHandler();
        clientHandler.addDisplay(display);
        readHandler.addDisplay(display);
    }

    public void run() {
        try{
            AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            Future<?> connectFuture = client.connect(new InetSocketAddress("localHost",3000));
            connectFuture.get();

            Charset charset = StandardCharsets.UTF_8;

            attachment.setClient(client);
            attachment.setReadMode(false);
            attachment.setByteBuffer(ByteBuffer.allocate(1024));
//            attachment.getByteBuffer().put(charset.encode("Client1 is entered"));
//            attachment.getByteBuffer().flip();

//            ByteBuffer buffer = charset.encode("Client1 is entered");
//            buffer.flip();
//            client.write(buffer);

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void writeToServer(String msg){
        attachment.setReadMode(false);
        attachment.getByteBuffer().clear();
        attachment.getByteBuffer().put(charset.encode(msg));
        attachment.getByteBuffer().flip();
        attachment.getClient().write(attachment.getByteBuffer(), attachment,clientHandler);
    }
    public void readFromServer(){
        attachment.getClient().read(attachment.getByteBuffer(),attachment,readHandler);
    }
}