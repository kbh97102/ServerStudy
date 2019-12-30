package connect;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(10001);
            System.out.println("Waiting Connect ..");

            Socket socket = server.accept();

            InetAddress inetAddress = socket.getInetAddress();
            System.out.println(inetAddress.getHostAddress()+"호스트 주소");

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            while((line = bufferedReader.readLine())!=null){
                printWriter.println(line);
                printWriter.flush();
            }

            printWriter.close();
            bufferedReader.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
