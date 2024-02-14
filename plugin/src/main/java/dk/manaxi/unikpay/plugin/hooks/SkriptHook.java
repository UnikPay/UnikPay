package dk.manaxi.unikpay.plugin.hooks;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.manaxi.unikpay.plugin.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkriptHook extends Hook {
    @Getter
    private static SkriptAddon addon = null;
    @Getter
    private static Skript Instance = null;

    public SkriptHook() {
        super("Skript", dk.manaxi.unikpay.plugin.enums.Hook.SKRIPT);
    }

    @Override
    public boolean init(JavaPlugin paramPlugin) {
        if (!isEnabled())
            return false;

        if(!Main.getInstance().getConfigSystem().getSKRIPTHOOK())
            return false;

        try {
            Instance = (Skript) Bukkit.getPluginManager().getPlugin("Skript");
            try {
                addon = Skript.registerAddon(Main.getInstance());
                addon.loadClasses("dk.manaxi.unikpay.plugin", "skript");
            } catch (IOException exception) {
                Main.getInstance().getInternalLang().send(Main.getInstance().getAdventure().console(), "console.errorSkript");
                Main.getInstance().getLogger().severe("An error occurred while loading the skript addon: " + exception.getMessage());
            }
            Main.getInstance().getInternalLang().send(Main.getInstance().getAdventure().console(), "console.successSkript");

            return Instance != null;

        } catch (Exception e) {
            System.out.print(e);
            return false;
        }

    }
}
