package dk.manaxi.paygradle;

import dk.manaxi.paygradle.commands.PayCommand;
import dk.manaxi.paygradle.listener.PayListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PayGradle extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("pay").setExecutor(new PayCommand());
        Bukkit.getPluginManager().registerEvents(new PayListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
