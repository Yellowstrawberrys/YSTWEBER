package cf.yellowstrawberry.ystweber.httpConnection;

import java.net.Socket;

public class connectionHandler extends Thread{

    Socket soc;

    public connectionHandler(Socket soc){
        this.soc = soc;
    }
}
