package cf.yellowstrawberry.ystweber.Console.Commands;

import cf.yellowstrawberry.ystweber.api.WebPluginAPI.Console.CommandHandler;
import cf.ystapi.Logging.Logger;

public class stop implements CommandHandler {
    @Override
    public String name() {
        return "stop";
    }

    @Override
    public String[] alias() {
        return new String[0];
    }

    @Override
    public void onCalled() {
        Logger.getLoggerByName("YSTWeber").info("Program will exit with exit code 0");
        System.exit(0);
    }
}
