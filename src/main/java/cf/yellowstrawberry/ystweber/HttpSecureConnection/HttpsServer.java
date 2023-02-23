package cf.yellowstrawberry.ystweber.HttpSecureConnection;

import cf.yellowstrawberry.ystweber.core.Main;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpsServer extends Thread{
    CertificateManager certManager;
    ServerSocket server;
    public HttpsServer(){
        try {
            loadCert();
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
                    new HttpsConnectionHandler(soc, certManager).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadCert(){
        certManager = new CertificateManager(new File(Main.RootFolder+"/ssl/public.der"), new File(Main.RootFolder+"/ssl/pub.der"), new File(Main.RootFolder+"/ssl/private.der"));
    }
}
