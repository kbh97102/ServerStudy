package connect;

import java.io.*;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("127.0.0.1",3000);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;

            while((line = keyboard.readLine())!=null){
                if(line.equals("quit")){
                    break;
                }
                printWriter.println(line);
//                printWriter.append(line);
                printWriter.flush();

                String echo = bufferedReader.readLine();
                System.out.println("Received Data : "+echo);


            }

            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}