package cf.yellowstrawberry.ystweber.api.WebPluginAPI.Console;

public interface CommandHandler {
    public String name();
    public String[] alias();
    public void onCalled();
}
