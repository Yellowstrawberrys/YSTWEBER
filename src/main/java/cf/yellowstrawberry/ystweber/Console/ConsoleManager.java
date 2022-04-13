package cf.yellowstrawberry.ystweber.Console;

import cf.yellowstrawberry.ystweber.Console.Commands.stop;
import cf.yellowstrawberry.ystweber.api.WebPluginAPI.Console.CommandHandler;

import java.util.HashMap;

public class ConsoleManager {

    ConsoleHandler con = new ConsoleHandler();

    public HashMap<String, CommandHandler> commands = new HashMap<>();

    public void init(){
        con.setPriority(1);
        con.start();
        addCommand("stop", new stop());
    }

    public void addCommand(String name, CommandHandler commandHandler){
        commands.put(name, commandHandler);
    }
}
