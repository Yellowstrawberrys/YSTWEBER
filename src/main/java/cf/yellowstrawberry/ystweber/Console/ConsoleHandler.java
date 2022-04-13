package cf.yellowstrawberry.ystweber.Console;

import cf.yellowstrawberry.ystweber.core.Main;
import cf.ystapi.Logging.Logger;

import java.util.Scanner;

public class ConsoleHandler extends Thread{
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("YST-Console > ");
            String command = sc.nextLine();
            if(Main.consoleManager.commands.containsKey(command)){
                Main.consoleManager.commands.get(command).onCalled();
            }else
                Logger.getLoggerByName("YSTWeber").error("That command does not exist.");
        }
    }
}
