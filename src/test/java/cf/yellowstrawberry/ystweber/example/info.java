package cf.yellowstrawberry.ystweber.example;

import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPlugin;
import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPluginInformation;

public class info implements WebPluginInformation {

    @Override
    public String Name() {
        return "example";
    }

    @Override
    public String Description() {
        return "This Plugin is Example Plugin";
    }

    @Override
    public String Version() {
        return "V0.0.0.2";
    }

    @Override
    public String[] Authors() {
        return new String[]{"Yellowstrawberry"};
    }

    @Override
    public String[] RequiredConfig() {
        return new String[]{"UsingExamplePlugin"};
    }

    @Override
    public Class<? extends WebPlugin> MainClass() {
        return main.class;
    }
}
