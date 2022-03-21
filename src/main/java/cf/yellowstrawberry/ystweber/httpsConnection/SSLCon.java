package cf.yellowstrawberry.ystweber.httpsConnection;

import cf.yellowstrawberry.ystweber.httpConnection.connectionHandler;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SSLCon extends Thread{
    SSLServerSocket server;
    public SSLCon(){
        try {
            server = new SSLServerSocketFactory().createServerSocket(443);
        } catch (IOException e) {
            System.out.println("Failed to Start SSL Server On Port 443\n" +
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
                    new HttpsConnectionHandler(soc).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
