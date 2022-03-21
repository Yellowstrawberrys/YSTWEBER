package cf.yellowstrawberry.ystweber.httpConnection;

import cf.yellowstrawberry.ystweber.core.Main;
import cf.yellowstrawberry.ystweber.sender.Sender;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class connectionHandler extends Thread{

    Socket soc;
    String path;

    public connectionHandler(Socket soc){
        this.soc = soc;
    }

    @Override
    public void run() {
        try {
            String line;
            List<String> liness = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                liness.add(line);
            }
            String[] lines = liness.toArray(new String[]{});
            if(!lines[0].startsWith("GET"))
                Sender.returnWithStatusCode(soc.getOutputStream(), 405, soc.getLocalAddress().getHostAddress(), Main.port);
            else {
                String parameter = null;
                Matcher matcher = Pattern.compile("GET (.*)"+(lines[0].contains("?") ? "\\?(.*)" : "(.*)")+" HTTP/(.*)").matcher(lines[0]);
                if (matcher.find()) {
                    path = matcher.group(1);
                    parameter = matcher.group(2);
                }
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
                    Sender.sendContent(soc.getOutputStream(), ls, content.getAbsolutePath());
                }else
                    Sender.returnWithStatusCode(soc.getOutputStream(), 404, soc.getLocalAddress().getHostAddress(), Main.port);
            }
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
