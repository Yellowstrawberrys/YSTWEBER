package cf.yellowstrawberry.ystweber.httpsConnection;

import cf.yellowstrawberry.ystweber.httpConnection.connectionHandler;
import cf.yellowstrawberry.ystweber.httpConnection.httpCon;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SSLCon extends Thread{
    ServerSocket server;
    public SSLCon(){
        try {
            //ServerSocketFactory sf = SSLServerSocketFactory.getDefault();
            //server = sf.createServerSocket(443);
            server = new ServerSocket(443);
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
                    System.out.println("f");
                    new HttpsConnectionHandler(soc).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
