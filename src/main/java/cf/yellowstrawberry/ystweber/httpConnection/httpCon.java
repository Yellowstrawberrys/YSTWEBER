package cf.yellowstrawberry.ystweber.httpConnection;

import cf.yellowstrawberry.ystweber.core.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class httpCon extends Thread{
    ServerSocket server;
    public httpCon(){
        try {
            server = new ServerSocket(81);
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
                    if(Main.configs.containsKey(soc.getLocalAddress().getHostAddress())){
                        new connectionHandler(soc);
                    }else{

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
