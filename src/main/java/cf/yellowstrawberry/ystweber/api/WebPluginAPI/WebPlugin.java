package cf.yellowstrawberry.ystweber.api.WebPluginAPI;

import cf.yellowstrawberry.ystweber.api.Object.Request;

public class WebPlugin {
    /**
     * @Param OnPlugin Enabled
     * */
    public void onEnabled(){}
    /**
     * @Param OnPlugin Disabled
     * */
    public void onDisabled(){}

    /**
     * @Param On Any Request
     * */
    public void onRequest(Request request){}
}
