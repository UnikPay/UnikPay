package dk.manaxi.unikpay.plugin.interfaces;

import dk.manaxi.unikpay.plugin.enums.Hook;
import org.bukkit.plugin.java.JavaPlugin;

public interface IHook {
    String getName();

    Hook getEnum();

    boolean isEnabled();

    boolean init(JavaPlugin paramJavaPlugin);
}
