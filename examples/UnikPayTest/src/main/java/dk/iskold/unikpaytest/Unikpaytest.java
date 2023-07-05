package dk.iskold.unikpaytest;

import dk.iskold.unikpaytest.commands.anmod;
import dk.iskold.unikpaytest.listener.Betaling;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Unikpaytest extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("anmod").setExecutor(new anmod());
        Bukkit.getPluginManager().registerEvents(new Betaling(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
