package cf.yellowstrawberry.ystweber.api;

public interface WebPluginInformation {
    String Name();
    String Description();
    String[] Authors();
    String[] RequiredConfig();
    Class<? extends WebPlugin> MainClass();
}
