package cf.yellowstrawberry.ystweber.core;

import cf.yellowstrawberry.ystweber.Console.ConsoleManager;
import cf.yellowstrawberry.ystweber.httpConnection.HttpServer;
import cf.yellowstrawberry.ystweber.HttpSecureConnection.HttpsServer;
import cf.yellowstrawberry.ystweber.plugin.PluginManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static String version = "0.0.1";
    public static String os = "";
    public static int port = 81;
    public static int mode = 0; //Unix = 0; Windows = 1;
    public static File confFolder;
    public static File webConfFolder;
    public static File webRootFolder;
    public static File RootFolder;
    public static Map<String, File> webRoots = new HashMap<>();
    public static Properties configFile = new Properties();
    public static Map<String, Properties> configs = new HashMap<>();
    public static Logger logger = Logger.getLogger("YSTWebber");
    public static ConsoleManager consoleManager;

    public static void main(String[] args) throws IOException {
        System.out.print("\n \n" +
                "\n" +
                "          __   __   ___    _____         __      __ ___     ___     ___     ___   \n" +
                "    o O O \\ \\ / /  / __|  |_   _|        \\ \\    / /| __|   | _ )   | __|   | _ \\  \n" +
                "   o       \\ V /   \\__ \\    | |      _    \\ \\/\\/ / | _|    | _ \\   | _|    |   /  \n" +
                "  TT__[O]  _|_|_   |___/   _|_|_   _(_)_   \\_/\\_/  |___|   |___/   |___|   |_|_\\  \n" +
                " {======|_| \"\"\" |_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
                "./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' \n" +
                "               Version: "+version+"\n");
        logger.info("Starting YSTWeber...\n");
        logger.log(Level.CONFIG, "\nSystem Info -------------------\n" +
                "OS NAME: "+System.getProperty("os.name")+"\n" +
                "YSTWeber Version: "+version+"\n" +
                "-------------------------------\n");
        os = System.getProperty("os.name");
        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            logger.log(Level.CONFIG, "Setting OS mode to Windows mode");
            mode = 1;
        }else{
            logger.log(Level.CONFIG, "Setting OS mode to Unix mode");
            mode = 0;
        }
        RootFolder = new File((mode == 0) ? "/ystweber/" : "C:/ystweber/");
        confFolder = new File(RootFolder+"/config/");
        webConfFolder = new File(RootFolder+"/sites");
        webRootFolder = new File(RootFolder+"/webroot");

        //Site Config Checker
        File properties = new File(confFolder+"/ystweber.properties");
        if(!webConfFolder.exists() && !webConfFolder.isDirectory()){
            logger.log(Level.WARNING,"Site Folder has been not found, Creating Site Folder");
            if(webConfFolder.mkdirs()) {
                logger.info("webConfFolder has been successfully created at '" + webConfFolder.getAbsolutePath() + "'");
                File defaultConf = new File(webConfFolder+"/default.properties");
                defaultConf.createNewFile();
                Properties proper = new Properties();
                new File(new File(webRootFolder.getAbsolutePath()+"/default/").getAbsolutePath()).mkdirs();
                proper.load(new FileInputStream(defaultConf));
                proper.put("ServerName", "default");
                proper.put("DocumentRoot", webRootFolder.getAbsolutePath()+"/default/");
                proper.store(new FileOutputStream(defaultConf), "Default Config File\n\nServerName need to be your IP(Except this document)");
            }else
                logger.log(Level.SEVERE, "Failed to create webConfFolder");
        }


        //Load Site Config
        loadSiteConfig();

        //Checking webRootFolder/ConfigFolder
        if(!webRootFolder.exists() && !webRootFolder.isDirectory()){
            logger.log(Level.WARNING,"webRootFolder has been not found, Creating webRootFolder");
            if(webRootFolder.mkdirs())
                logger.info("webRootFolder has been successfully created at '"+webRootFolder.getAbsolutePath()+"'");
            else
                logger.log(Level.WARNING,"Failed to create webRootFolder");
        }
        if(!(confFolder.exists() && confFolder.isDirectory())){
            logger.log(Level.WARNING,"Config Folder has been not found, Creating Config Folder/File");
            confFolder.mkdirs();
            try {
                properties.createNewFile();
                configFile.load(new FileInputStream(properties));
                configFile.put("Log", RootFolder+"/logs/info.log");
                configFile.put("AccessLog", RootFolder+"/logs/access.log");
                configFile.put("ErrorLog", RootFolder+"/logs/error.log");
                configFile.store(new FileOutputStream(properties), null);
                logger.info("Config File has been Successfully Created");
            } catch (Exception e) {
                logger.log(Level.SEVERE,"Failed to Create Config File\n" +
                        "Program will exit with exit code 0");
                System.exit(0);
            }
        }else {
            logger.info("Config Folder has been found, Reading Config Files");
            logger.info("Success to Read Config File");
        }

        //Starting Plugin Manager
        new PluginManager().init();

        //Starting Console Manager
        consoleManager = new ConsoleManager();
        consoleManager.init();

        //Starting WebServer
        new HttpServer(port).start();
        new HttpsServer().start();
    }

    private static void loadSiteConfig() {
        logger.info("Loading Site Configs...");
        for(File f : Objects.requireNonNull(webConfFolder.listFiles(f -> f.getName().endsWith(".properties")))){
            try {
                Properties proper = new Properties();
                proper.load(new FileInputStream(f));
                if(!proper.containsKey("Disabled") || proper.getProperty("Disabled").equals("0")){
                    if(proper.getProperty("ServerName") != null)
                        configs.put(proper.getProperty("ServerName").toLowerCase(), proper);
                    else
                        logger.log(Level.WARNING,f.getAbsolutePath()+" doesn't have 'ServerName' property");
                }
            } catch (IOException e) {
                logger.log(Level.WARNING,"Failed to read "+f.getAbsolutePath()+" file.");
            }
        }
        logger.info("Successfully loaded %s of site(s)!".formatted(Objects.requireNonNull(webConfFolder.listFiles(f -> f.getName().endsWith(".properties"))).length));
    }
}
