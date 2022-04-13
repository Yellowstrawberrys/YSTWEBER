package cf.yellowstrawberry.ystweber.httpConnection;

import cf.yellowstrawberry.ystweber.api.Enums.METHOD;
import cf.yellowstrawberry.ystweber.api.Object.Request;
import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPlugin;
import cf.yellowstrawberry.ystweber.core.Main;
import cf.yellowstrawberry.ystweber.plugin.PluginManager;
import cf.yellowstrawberry.ystweber.plugin.YSTWeberPlugin;
import cf.yellowstrawberry.ystweber.sender.Sender;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class connectionHandler extends Thread{

    Socket soc;
    String path;
    boolean secured;

    public connectionHandler(Socket soc, boolean secured){
        this.soc = soc;
        this.secured = secured;
    }

    @Override
    public void run() {
        try {
            String line;
            String handshake = "";
            List<String> liness = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                if(line.startsWith("Sec-WebSocket-Key: "))
                    handshake = line.substring("Sec-WebSocket-Key: ".length());
                liness.add(line);
            }
            String[] lines = liness.toArray(new String[]{});
            String parameter = null;
            METHOD method = null;
            Matcher matcher = Pattern.compile("(.*) (.*)"+(lines[0].contains("?") ? "\\?(.*)" : "(.*)")+" HTTP/(.*)").matcher(lines[0]);
            if (matcher.find()) {
                method = METHOD.valueOf(matcher.group(1));
                path = matcher.group(2);
                parameter = matcher.group(3);
            }
            //Send Event
            for(WebPlugin pl : PluginManager.plugins)
                pl.onRequest(new Request(soc, path, method));
            //Send File
            File webroot;
            if (Main.configs.containsKey(soc.getLocalAddress().getHostAddress()))
                webroot = new File(Main.configs.get(soc.getLocalAddress().getHostName()).getProperty("DocumentRoot"));
            else
                webroot = new File(Main.configs.get("default").getProperty("DocumentRoot"));
            File content;
            if (path.endsWith("/") || !path.contains("."))
                content = new File(webroot.getAbsolutePath() + path +(!path.contains(".") ? "/" : "")+ "index.html");
            else
                content = new File(webroot.getAbsolutePath() + path);
            if (content.exists()) {
                String l;
                String ls = "";
                BufferedReader contents = new BufferedReader(new FileReader(content));
                while ((l = contents.readLine()) != null)
                    ls += l;
                Sender.sendContent(soc.getOutputStream(), ls, content.getAbsolutePath(), secured, handshake);
            }else
                Sender.returnWithStatusCode(soc.getOutputStream(), 404, soc.getLocalAddress().getHostAddress(), Main.port, secured, handshake);
//            System.out.println(lines+" / "+SecWebCode);
            soc.getOutputStream().flush();
            soc.getOutputStream().close();
            soc.close();
            return;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }
}
