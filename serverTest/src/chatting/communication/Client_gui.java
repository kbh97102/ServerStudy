package chatting.communication;

import chatting.core.Attachment;
import chatting.hadler.forClient.ClientHandler;
import chatting.hadler.forClient.ClientReadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class Client_gui {

    private Consumer<String> display;
    private ClientHandler clientHandler;
    private Charset charset = StandardCharsets.UTF_8;
    private Attachment attachment = new Attachment();
    private ClientReadHandler readHandler = new ClientReadHandler();

    public Client_gui(Consumer<String> display) {
        this.display = display;
        clientHandler = new ClientHandler();
        clientHandler.addDisplay(display);
        readHandler.addDisplay(display);
    }

    public void run() {
        try {
            AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            Future<?> connectFuture = client.connect(new InetSocketAddress("localHost", 3000));
            connectFuture.get(3, TimeUnit.SECONDS);

            Charset charset = StandardCharsets.UTF_8;

            attachment.setClient(client);
            attachment.setReadMode(false);
            attachment.setBuffer(ByteBuffer.allocate(1024));

            readFromServer();

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        catch (TimeoutException t){
            System.out.println("TimeOut");
            System.exit(0);
        }
    }

    /**
     * Send Data with UserInput( TextField Data)
     *
     * @param msg Message
     */
    public void writeToServer(String msg) {
        attachment.getBuffer().clear();
        attachment.getBuffer().put(charset.encode(msg));
        attachment.getBuffer().flip();
        attachment.getClient().write(attachment.getBuffer());
        System.out.println("Client Send");
    }

    public void readFromServer() {
        attachment.getBuffer().clear();
        attachment.getClient().read(attachment.getBuffer(), attachment, readHandler);
    }

    public void addDisplay(Consumer<String> display) {
        this.display = display;
    }
}