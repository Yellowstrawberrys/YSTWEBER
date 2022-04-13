package cf.yellowstrawberry.ystweber.httpsConnection;

import cf.yellowstrawberry.ystweber.core.Main;
import cf.yellowstrawberry.ystweber.sender.Sender;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpsConnectionHandler extends Thread {

    Socket soc;
    String path;

    public HttpsConnectionHandler(Socket soc){
        this.soc = soc;
    }

    @Override
    public void run() {
        try {
            String line;
            String handshake = "";
            List<String> liness = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            String[] clientHello = bytesToHex(bufferedReader.readLine().getBytes(StandardCharsets.UTF_8));
            int handShakeRecord = Integer.parseInt(clientHello[0]);
            String protocolVersion = clientHello[1]+"."+clientHello[2];
            boolean isClientSentHello = Integer.parseInt(clientHello[5]) == 0x01;
            String clientVersion = clientHello[11]+"."+clientHello[12];

            System.out.println("Client Hello:\n"+ Arrays.toString(clientHello) +"\nhandShakeRecord: "+handShakeRecord+"\nProtocol Version: "+protocolVersion+"\nisClientSentHello: "+isClientSentHello
                    +"\nClientVersion: "+clientVersion);

            String[] clientHello2 = bytesToHex(bufferedReader.readLine().getBytes(StandardCharsets.UTF_8));
            System.out.println(Arrays.toString(clientHello2));
            byte[] b = new byte[28];
            new Random().nextBytes(b);
            String recordHeader = "1603030031";
            String handShakeHeader = "0200002d";
            String serverHelloString = "160303003100200002d0303"+Long.toHexString(System.currentTimeMillis())+bytesToHexAsString(b);
            byte[] serverHello = HexFormat.of().parseHex(serverHelloString);
            System.out.println(Arrays.toString(serverHello));
            soc.getOutputStream().write(serverHello);
////            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()){
////                if(line.startsWith("Sec-WebSocket-Key: "))
////                    handshake = line.substring("Sec-WebSocket-Key: ".length());
////                liness.add(line);
////            }
//            String[] lines = liness.toArray(new String[]{});
//            if(!lines[0].startsWith("GET"))
//                Sender.returnWithStatusCode(soc.getOutputStream(), 405, soc.getLocalAddress().getHostAddress(), Main.port, secured, handshake);
//            else {
//                String parameter = null;
//                Matcher matcher = Pattern.compile("GET (.*)"+(lines[0].contains("?") ? "\\?(.*)" : "(.*)")+" HTTP/(.*)").matcher(lines[0]);
//                if (matcher.find()) {
//                    path = matcher.group(1);
//                    parameter = matcher.group(2);
//                }
//                File webroot;
//                if (Main.configs.containsKey(soc.getLocalAddress().getHostAddress()))
//                    webroot = new File(Main.configs.get(soc.getLocalAddress().getHostName()).getProperty("DocumentRoot"));
//                else
//                    webroot = new File(Main.configs.get("default").getProperty("DocumentRoot"));
//                File content;
//                if (path.endsWith("/") || !path.contains("."))
//                    content = new File(webroot.getAbsolutePath() + path +(!path.contains(".") ? "/" : "")+ "index.html");
//                else
//                    content = new File(webroot.getAbsolutePath() + path);
//                if (content.exists()) {
//                    String l;
//                    String ls = "";
//                    BufferedReader contents = new BufferedReader(new FileReader(content));
//                    while ((l = contents.readLine()) != null)
//                        ls += l;
//                    Sender.sendContent(soc.getOutputStream(), ls, content.getAbsolutePath(), secured, handshake);
//                }else
//                    Sender.returnWithStatusCode(soc.getOutputStream(), 404, soc.getLocalAddress().getHostAddress(), Main.port, secured, handshake);
//            }
////            System.out.println(lines+" / "+SecWebCode);
            soc.getOutputStream().flush();
            //soc.getOutputStream().close();
            //soc.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String[] bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        String[] val = new String[bytes.length];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            val[j] = hexChars[j * 2]+""+hexChars[j * 2 + 1];
        }
        return val;
    }
    public static String bytesToHexAsString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
