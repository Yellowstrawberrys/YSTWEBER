package cf.yellowstrawberry.ystweber.Console;

import cf.yellowstrawberry.ystweber.core.Main;

import java.util.Scanner;
import java.util.logging.Logger;

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
                System.out.println("That command does not exist.");
        }
    }
}
