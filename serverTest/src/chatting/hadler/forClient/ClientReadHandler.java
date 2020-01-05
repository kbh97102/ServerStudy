package chatting.hadler.forClient;

import result.Attachment;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class ClientReadHandler implements CompletionHandler<Integer, Attachment> {

    private Consumer<String> display;

    public void addDisplay(Consumer<String> display){
        this.display = display;
    }

    @Override
    public void completed(Integer result, Attachment attachment) {
        ByteBuffer buffer = attachment.getByteBuffer();
        buffer.flip();
        Charset charset = StandardCharsets.UTF_8;
        display.accept(charset.decode(buffer).toString());
        buffer.clear();

        attachment.getClient().read(attachment.getByteBuffer(),attachment,this);
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("Client Read Failed");
    }
}
