import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {

        // MainController.getMainController().start();


        new Thread(Main::server).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(Main::client).start();



    }

    static void client() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 10086);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            int i = 0;
            while (i < 10) {
                // byte[] bytes = out.readAllBytes();
                out.write("Hello World!");
                // Thread.sleep(2000);
                i++;
            }
            out.write("\r");
            out.flush();
            //out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static void server() {
        try {
            ServerSocket serverSocket = new ServerSocket(10086);
            Socket clientSocket = serverSocket.accept();
            BufferedReader bfIs = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            {
                String str = bfIs.readLine();
                if(str != null) {
                    // Thread.sleep(5000);
                    System.out.println(str);
                }
                // Thread.sleep(5000);
            }

            // serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}