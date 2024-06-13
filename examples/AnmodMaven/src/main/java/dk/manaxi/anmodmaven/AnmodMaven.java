package dk.manaxi.anmodmaven;

import dk.manaxi.anmodmaven.commands.AnmodCommand;
import dk.manaxi.anmodmaven.listener.BetalingListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnmodMaven extends JavaPlugin {

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
