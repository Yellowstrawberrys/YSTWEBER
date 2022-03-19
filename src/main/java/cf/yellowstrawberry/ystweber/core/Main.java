package cf.yellowstrawberry.ystweber.core;

import cf.yellowstrawberry.ystweber.httpConnection.httpCon;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {

    public static String version = "";
    public static String mode = "";
    public static File confFolder;
    public static File webConfFolder;
    public static Properties configFile = new Properties();
    public static Map<String, Properties> configs = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Starting YSTWeber");
        System.out.println("System Info -------------------\n" +
                "OS NAME: "+System.getProperty("os.name")+"\n" +
                "YSTWeber Version: "+version+"\n" +
                "-------------------------------");
        new httpCon().start();
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            System.out.println("Setting OS mode to Windows mode");
            mode = "windows";
            confFolder = new File("C:/ystweber/config/");
            webConfFolder = new File("C:/ystweber/sites/");

            File properties = new File(confFolder+"/ystweber.properties");
            if(!(confFolder.exists() && confFolder.isDirectory())){
                System.out.println("Config Folder has been not found, Creating Config Folder/File");
                confFolder.mkdirs();
                try {
                    properties.createNewFile();
                    configFile.load(new FileInputStream(properties));
                    configFile.put("Log", "C:/ystweber/log/info.log");
                    configFile.put("AccessLog", "C:/ystweber/log/access.log");
                    configFile.put("ErrorLog", "C:/ystweber/log/error.log");
                    configFile.store(new FileOutputStream(properties), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Config Folder has been found, Reading Config Files");
            }
        }
    }
}
