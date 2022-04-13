package cf.yellowstrawberry.ystweber.sender;

import cf.yellowstrawberry.ystweber.core.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static cf.yellowstrawberry.ystweber.sender.Types.codeDescriptions;

public class Sender {

    public static String error = """
            <html>
            <body>
            <h1>%s(%s)</h1>
            <p>Please contact to site administrator</p>
            <hr/>
            <address>YSTWeber/%s (%s) Server at %s Port %s</address>
            </body>
            </html>""";

    public static void sendContent(OutputStream out, String content, String path, boolean isSecured, String SecWebCode) throws NoSuchAlgorithmException, IOException {
        String type = Types.media_types.get(path.split("\\.")[path.split("\\.").length-1]);
        if(type.startsWith("video") || type.startsWith("application") || type.startsWith("image")){
            sendContent(out, path, Types.media_types.get(path.split("\\.")[path.split("\\.").length-1]), 200, "OK", true, isSecured, SecWebCode);
        }else
            sendContent(out, content, Types.media_types.get(path.split("\\.")[path.split("\\.").length-1]), 200, "OK", false, isSecured, SecWebCode);
    }

    public static void returnWithStatusCode(OutputStream out, int statusCode, String requestedUrl, int port, boolean isSecured, String SecWebCode) throws NoSuchAlgorithmException, IOException {
        sendContent(out, error.formatted(codeDescriptions.get(statusCode), statusCode, Main.version, Main.os, requestedUrl, port), "text/html", statusCode, codeDescriptions.get(statusCode), false, isSecured, SecWebCode);
    }

    private static void sendContent(OutputStream out, String content, String contentType, int code, String codeDescription, boolean isFile, boolean isSecured, String SecWebCode) throws IOException {
        String response = "";
        if(isSecured){
            try {
                response = ("HTTP/1.1 101 Switching Protocols\r\n" +
                        "Upgrade: websocket\r\n" +
                        "Server: YSTWeber/%s (%s)\r\n" +
                        "Connection: Upgrade\r\n" +
                        "Sec-WebSocket-Accept: %s\r\n\r\n").formatted(Main.version, System.getProperty("os.name"), Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest(SecWebCode.getBytes(StandardCharsets.UTF_8))));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }else
            response = ("HTTP/1.1 %s %s\r\n" +
                    "Date: %s\r\n" +
                    "Server: YSTWeber/%s (%s)\r\n" +
                    "Connection: Keep-Alive\r\n" +
                    "Keep-Alive: timeout=5, max=100\r\n" +
                    "Content-Length: %%s\r\n" +
                    "Content-Type: %s\r\n\r\n").formatted(code, codeDescription,
                    new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").format(new Date(System.currentTimeMillis())),
                    Main.version, System.getProperty("os.name"),
                    contentType);
        if(isFile){
            InputStream ipt = new FileInputStream(content);
            response = response.formatted(new File(content).length());
            out.write(response.getBytes(StandardCharsets.UTF_8), 0, response.length());
            byte[] buffer = new byte[8 * 1460];
            int bytesRead;
            while ((bytesRead = ipt.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ipt.close();
        }else{
            response = response.formatted(content.length());
            out.write(response.getBytes(StandardCharsets.UTF_8), 0, response.length());
            out.write(content.getBytes(StandardCharsets.UTF_8));
        }
        out.flush();
    }
}
