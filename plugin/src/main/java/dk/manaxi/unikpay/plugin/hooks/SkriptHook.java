package dk.manaxi.unikpay.plugin.hooks;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkriptHook extends Hook {
    private static SkriptAddon addon = null;
    private static Skript Instance = null;

    public SkriptHook() {
        super("Skript", dk.manaxi.unikpay.plugin.enums.Hook.SKRIPT);
    }

    @Override
    public boolean init(JavaPlugin paramPlugin) {
        if (!isEnabled())
            return false;

        if(!Main.configYML.getBoolean("skript-hook"))
            return false;

        try {
            Instance = (Skript) Bukkit.getPluginManager().getPlugin("Skript");
            try {
                addon = Skript.registerAddon(Main.getInstance());
                addon.loadClasses("dk.manaxi.unikpay.plugin", "skript");
            } catch (IOException exception) {
                Main.log.sendMessage(ColorUtils.getColored("   &c - SKRIPT COULD NOT BE HOOKED"));
                exception.printStackTrace();
            }
            Main.log.sendMessage(ColorUtils.getColored("   &a - SKRIPT HAS BEEN HOOKED"));

            return Instance != null;

        } catch (Exception e) {
            System.out.print(e);
            return false;
        }

    }

    public static Skript getInstance() {
        return Instance;
    }

    public static SkriptAddon getAddon() {
        return addon;
    }



}
