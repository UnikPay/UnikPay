package dk.manaxi.anmodgradle;

import dk.manaxi.anmodgradle.commands.AnmodCommand;
import dk.manaxi.anmodgradle.listener.BetalingListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnmodGradle extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("anmod").setExecutor(new AnmodCommand());
        Bukkit.getPluginManager().registerEvents(new BetalingListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
