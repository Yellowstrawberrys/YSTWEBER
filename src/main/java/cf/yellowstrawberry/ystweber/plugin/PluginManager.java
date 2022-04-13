package cf.yellowstrawberry.ystweber.plugin;

import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPlugin;
import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPluginInformation;
import cf.yellowstrawberry.ystweber.core.Main;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class PluginManager {

    File pluginFolder = new File(Main.RootFolder+"/plugins");
    //static HashSet<WebPluginInformation> plugins=new HashSet<>();
    public static HashSet<WebPlugin> plugins = new HashSet<>();
    ArrayList<String> classes=new ArrayList<>();
    ArrayList<URL> urls=new ArrayList<>();

    // TODO Make Plugin Loader
    public void init(){
        System.out.println("Initializing Plugin Manager");

        //Checking PluginFolder exists
        if(!(pluginFolder.exists() && pluginFolder.isDirectory())){
            System.out.println("Plugin Folder has been not found, Creating Plugin folder...");
            if(pluginFolder.mkdirs())
                System.out.println("Plugin Folder has been successfully created on '"+pluginFolder+"'");
            else{
                System.out.println("Failed to create plugin Folder\n" +
                        "Exiting with code 0");
                System.exit(0);
            }
        }

        //Loading Plugins From Plugin Folder
        System.out.println("Getting plugins from '"+pluginFolder+"'");
        for(File pl : Objects.requireNonNull(pluginFolder.listFiles(f -> f.getName().endsWith(".jar"))))
            loadPlugin(pl);
        initPlugins();
    }

    private void loadPlugin(final File pl) {
        try {
            JarFile jar = new JarFile(pl);
            jar.stream().forEach(jarEntry -> {
                if (jarEntry.getName().endsWith(".class")) {
                    classes.add(jarEntry.getName());
                }
            });
            URL url = pl.toURI().toURL();
            urls.add(url);
        }catch (Exception e){

        }
    }

    private void initPlugins(){
        URLClassLoader urlClassLoader=new URLClassLoader(urls.toArray(new URL[urls.size()]));
        classes.forEach(className->{
            try
            {
                Class cls=urlClassLoader.loadClass(className.replaceAll("/",".").replace(".class",""));
                Class[] interfaces=cls.getInterfaces();
                for(Class intface:interfaces)
                {
                    if(intface.equals(WebPluginInformation.class))
                    {
                        WebPluginInformation plugin=(WebPluginInformation) cls.newInstance();
                        YSTWeberPlugin pl = new YSTWeberPlugin(plugin);
                        System.out.println("Enabling Plugin "+plugin.Name()+"...");
                        plugins.add(plugin.MainClass().newInstance());
                        break;
                    }
                }
            }
            catch (Exception e){e.printStackTrace();}
        });
    }
}
