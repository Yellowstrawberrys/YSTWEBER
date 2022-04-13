package cf.yellowstrawberry.ystweber.api.Object;

import cf.yellowstrawberry.ystweber.api.Enums.METHOD;

import java.io.*;
import java.net.Socket;

public class Request {

    final String requestedURI;
    final String requestedPath;
    final Socket soc;
    final METHOD requestedMethod;

    public Request(Socket soc, String requestedPath, METHOD requestedMethod){
        this.requestedURI = soc.getLocalAddress().getHostAddress();
        this.requestedPath = requestedPath;
        this.soc = soc;
        this.requestedMethod = requestedMethod;
    }

    public Socket getSocket(){
        return this.soc;
    }

    public String getRequestedPath(){
        return this.requestedPath;
    }

    public String getRequestedURI(){
        return this.requestedURI;
    }

    public METHOD getMethod(){
        return this.requestedMethod;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.soc.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return this.soc.getInputStream();
    }

    public OutputStreamWriter getOutputStreamWriter() throws IOException {
        return new OutputStreamWriter(this.soc.getOutputStream());
    }

    public InputStreamReader getInputStreamReader() throws IOException {
        return new InputStreamReader(this.soc.getInputStream());
    }
}