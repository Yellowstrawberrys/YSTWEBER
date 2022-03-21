package cf.yellowstrawberry.ystweber.plugin;

import cf.yellowstrawberry.ystweber.api.WebPluginInformation;
import cf.yellowstrawberry.ystweber.core.Main;
import com.sun.source.util.Plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

public class PluginManager {

    File pluginFolder = new File(Main.RootFolder+"/plugins");

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
        System.out.println("Starting to load plugins from '"+pluginFolder+"'");

        for(File pl : Objects.requireNonNull(pluginFolder.listFiles(f -> f.getName().endsWith(".jar"))))
            loadPlugin(pl);
    }

    private void loadPlugin(final File pl) {
        System.out.println("Starting to Load plugin '"+pl+"'");
        final URLClassLoader plClsLoader = createPlClsLoader(pl);
        final ClassLoader clsLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(plClsLoader);
            for (WebPluginInformation plugin : ServiceLoader.load(WebPluginInformation.class, plClsLoader)) {
                System.out.println("Loading Plugin: "+plugin.Name());

            }
        } finally {
            Thread.currentThread().setContextClassLoader(clsLoader);
        }
    }

    private URLClassLoader createPlClsLoader(File plugin) {
        final URL[] urls = Arrays.stream(Optional.ofNullable(plugin.listFiles()).orElse(new File[]{}))
                .sorted()
                .map(File::toURI)
                .map(this::toUrl)
                .toArray(URL[]::new);
        return new PluginClassLoader(urls, getClass().getClassLoader());
    }

    private URL toUrl(final URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            System.out.println("Error while loading plugin\n" +
                    "Exiting with code 0");
            System.exit(0);
            return null;
        }
    }
}
