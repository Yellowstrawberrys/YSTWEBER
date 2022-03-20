package cf.yellowstrawberry.ystweber.plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class PluginClassLoader extends URLClassLoader {

    public static final List<String> api = List.of("cf.yellowstrawberry.ystweber.api");

    private final ClassLoader paClsLoader;

    public PluginClassLoader(URL[] urls, ClassLoader paClsLoader) {
        super(urls, paClsLoader);
        this.paClsLoader = paClsLoader;
    }
    @Override
    protected Class<?> loadClass(String name, boolean isResolved)
            throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            final boolean isApi = api.stream().anyMatch(name::startsWith);
            if (isApi) {
                loadedClass = paClsLoader.loadClass(name);
            } else {
                loadedClass = super.loadClass(name, isResolved);
            }
        }

        if (isResolved) {
            resolveClass(loadedClass);
        }
        return loadedClass;
    }
}
