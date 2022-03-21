package cf.yellowstrawberry.ystweber.api.Object;

import java.net.Socket;

public class Request {

    final String requestedURI;
    final String requestedPath;

    public Request(Socket soc, String requestedPath){
        this.requestedURI = soc.getLocalAddress().getHostAddress();
        this.requestedPath = "";
    }
}
