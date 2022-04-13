package cf.yellowstrawberry.ystweber.api.WebPluginAPI;

public interface WebPluginInformation {
    String Name();
    String Description();
    String Version();
    String[] Authors();
    String[] RequiredConfig();
    Class<? extends WebPlugin> MainClass();
}
