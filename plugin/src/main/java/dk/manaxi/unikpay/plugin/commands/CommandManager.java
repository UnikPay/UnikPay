package dk.manaxi.unikpay.plugin.commands;

import dk.manaxi.unikpay.plugin.commands.ingamecommands.UnikPayCommand;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager {
    public static void initialise(JavaPlugin instance) {
        UnikPayCommand unikPayCommand = new UnikPayCommand(instance, "unikpay");
        try {
            Bukkit.getPluginCommand("unikpay").setExecutor(unikPayCommand);
            Bukkit.getPluginCommand("unikpay").setTabCompleter(new TabCompleteListener(unikPayCommand));
            Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("   &a - Successful enabled commands"));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("   &c - Could not enable commands"));
        }


    }


}