package cf.yellowstrawberry.ystweber.plugin;

import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPlugin;
import cf.yellowstrawberry.ystweber.api.WebPluginAPI.WebPluginInformation;

public class YSTWeberPlugin {
    private String name;
    private String description;
    private String version;
    private String[] authors;
    private Class<? extends WebPlugin> mainClass;

    public YSTWeberPlugin(WebPluginInformation webPluginInformation){
        this.name = webPluginInformation.Name();
        this.description = webPluginInformation.Description();
        this.version = webPluginInformation.Version();
        this.authors = webPluginInformation.Authors();
        this.mainClass = webPluginInformation.MainClass();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public Class<? extends WebPlugin> getMainClass() {
        return mainClass;
    }

    public void setMainClass(Class<? extends WebPlugin> mainClass) {
        this.mainClass = mainClass;
    }
}
