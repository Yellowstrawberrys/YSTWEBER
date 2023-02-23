package cf.yellowstrawberry.ystweber.httpConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer extends Thread{
    ServerSocket server;
    public HttpServer(int port){
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Failed to Start Server On Port 80\n" +
                    "Exiting with code 0");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        while (true){
            Socket soc;
            try {
                if((soc = server.accept()) != null){
                    new connectionHandler(soc, false).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
