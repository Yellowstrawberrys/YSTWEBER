package cf.yellowstrawberry.ystweber.example;

import cf.yellowstrawberry.ystweber.api.Annotations.RequestPath;
import cf.yellowstrawberry.ystweber.api.WebPlugin;

public class main extends WebPlugin {
    @Override
    public void onEnabled() {
        System.out.println("Enabled Example Plugin");
    }

    @RequestPath(path = "/")
    public String onMainPath(){
        return "Welcome to Example Plugin!";
    }
}
