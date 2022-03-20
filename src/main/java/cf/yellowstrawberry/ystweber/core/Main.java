package cf.yellowstrawberry.ystweber.core;

import cf.yellowstrawberry.ystweber.httpConnection.httpCon;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class Main {

    public static String version = "0.0.1";
    public static String os = "";
    public static int port = 80;
    public static File confFolder;
    public static File webConfFolder;
    public static File webRootFolder;
    public static Map<String, File> webRoots = new HashMap<>();
    public static Properties configFile = new Properties();
    public static Map<String, Properties> configs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("\n" +
                "\n" +
                " __   __   ___    _____          __      __ ___     ___     ___     ___   \n" +
                " \\ \\ / /  / __|  |_   _|    o O O\\ \\    / /| __|   | _ )   | __|   | _ \\  \n" +
                "  \\ V /   \\__ \\    | |     o      \\ \\/\\/ / | _|    | _ \\   | _|    |   /  \n" +
                "  _|_|_   |___/   _|_|_   TS__[O]  \\_/\\_/  |___|   |___/   |___|   |_|_\\  \n" +
                "_| \"\"\" |_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
                "\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' \n" +
                "\n");
        System.out.println("Starting YSTWeber...\n");
        System.out.println("System Info -------------------\n" +
                "OS NAME: "+System.getProperty("os.name")+"\n" +
                "YSTWeber Version: "+version+"\n" +
                "-------------------------------\n");
        port=81;
        new httpCon().start();
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            System.out.println("Setting OS mode to Windows mode");
            os = System.getProperty("os.name");
            confFolder = new File("C:/ystweber/config/");
            webConfFolder = new File("C:/ystweber/sites/");
            webRootFolder = new File("C:/ystweber/webroot/");

            File properties = new File(confFolder+"/ystweber.properties");
            if(!webConfFolder.exists() && !webConfFolder.isDirectory()){
                System.out.println("Site Folder has been not found, Creating Site Folder");
                if(webConfFolder.mkdirs()) {
                    System.out.println("webConfFolder has been successfully created at '" + webConfFolder.getAbsolutePath() + "'");
                    File defaultConf = new File(webConfFolder+"/default.properties");
                    defaultConf.createNewFile();
                    Properties proper = new Properties();
                    new File(new File(webRootFolder.getAbsolutePath()+"/default/").getAbsolutePath()).mkdirs();
                    proper.load(new FileInputStream(defaultConf));
                    proper.put("ServerName", "default");
                    proper.put("DocumentRoot", webRootFolder.getAbsolutePath()+"/default/");
                    proper.store(new FileOutputStream(defaultConf), "Default Config File\n\nServerName need to be your IP(Except this document)");
                }else
                    System.out.println("Failed to create webConfFolder");
            }
            System.out.println("Reading Site Configs...");
            for(File f : Objects.requireNonNull(webConfFolder.listFiles(f -> f.getName().endsWith(".properties")))){
                try {
                    Properties proper = new Properties();
                    proper.load(new FileInputStream(f));
                    if(!proper.containsKey("Disabled") || proper.getProperty("Disabled").equals("0")){
                        if(proper.getProperty("ServerName") != null)
                            configs.put(proper.getProperty("ServerName").toLowerCase(), proper);
                        else
                            System.out.println(f.getAbsolutePath()+" doesn't have 'ServerName' property");
                    }
                } catch (IOException e) {
                    System.out.println("Failed to read "+f.getAbsolutePath()+" file.");
                }
            }
            System.out.println(configs.entrySet());
            if(!webRootFolder.exists() && !webRootFolder.isDirectory()){
                System.out.println("webRootFolder has been not found, Creating webRootFolder");
                if(webRootFolder.mkdirs())
                    System.out.println("webRootFolder has been successfully created at '"+webRootFolder.getAbsolutePath()+"'");
                else
                    System.out.println("Failed to create webRootFolder");
            }
            if(!(confFolder.exists() && confFolder.isDirectory())){
                System.out.println("Config Folder has been not found, Creating Config Folder/File");
                confFolder.mkdirs();
                try {
                    properties.createNewFile();
                    configFile.load(new FileInputStream(properties));
                    configFile.put("Log", "C:/ystweber/logs/info.log");
                    configFile.put("AccessLog", "C:/ystweber/logs/access.log");
                    configFile.put("ErrorLog", "C:/ystweber/logs/error.log");
                    configFile.store(new FileOutputStream(properties), null);
                    System.out.println("Config File has been Successfully Created");
                } catch (Exception e) {
                    System.out.println("Failed to Create Config File\n" +
                            "Program will exit with exit code 0");
                    System.exit(0);
                }
            }else {
                System.out.println("Config Folder has been found, Reading Config Files");
                System.out.println("Success to Read Config File");
            }
        }
    }
}
