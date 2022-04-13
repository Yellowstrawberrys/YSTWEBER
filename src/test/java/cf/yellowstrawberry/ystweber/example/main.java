package cf.yellowstrawberry.ystweber.example;

import cf.yellowstrawberry.ystweber.api.Object.Request;
import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPlugin;

public class main extends WebPlugin {
    @Override
    public void onEnabled() {
        System.out.println("Enabled Example Plugin");
    }

    @Override
    public void onRequest(Request request) {
        System.out.println("Got Request with method "+request.getMethod());
    }
}
