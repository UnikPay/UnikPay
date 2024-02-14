package dk.manaxi.unikpay.plugin.commands;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ingamecommands.UnikPayCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {
    public static void initialise(JavaPlugin instance) {
        UnikPayCommand unikPayCommand = new UnikPayCommand(instance, "unikpay");
        try {
            Bukkit.getPluginCommand("unikpay").setExecutor(unikPayCommand);
            Bukkit.getPluginCommand("unikpay").setTabCompleter(new TabCompleteListener(unikPayCommand));
            Main.getInstance().getInternalLang().send(Main.getInstance().getAdventure().console(), "console.successCommands");
        } catch (Exception e) {
            Main.getInstance().getInternalLang().send(Main.getInstance().getAdventure().console(), "console.errorCommands");
        }


    }


}