package connect;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client1 {
    private static SocketChannel socket;
    public Client1(SocketChannel socket){
        Client1.socket =socket;
    }
    public static void main(String[] args) {
        try{
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            Charset charset = StandardCharsets.UTF_8;
            ByteBuffer byteBuffer;
            String line = null;

            while((line = keyboard.readLine())!=null){
                if(line.equals("quit")){
                    break;
                }
                byteBuffer = charset.encode(line);
                socket.write(byteBuffer);
                byteBuffer.flip();
                socket.read(byteBuffer);

                System.out.println("Received Data : "+new String(byteBuffer.array(),charset));
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}