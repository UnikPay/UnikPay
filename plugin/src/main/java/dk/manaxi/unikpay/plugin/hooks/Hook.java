package dk.manaxi.unikpay.plugin.hooks;

import com.sun.istack.internal.NotNull;
import dk.manaxi.unikpay.plugin.interfaces.IHook;
import org.bukkit.Bukkit;


public abstract class Hook implements IHook {
    private final String name;

    private final dk.manaxi.unikpay.plugin.enums.Hook hook;

    private final boolean isEnabled;

    public Hook(String paramString, dk.manaxi.unikpay.plugin.enums.Hook paramHook) {
        this.name = paramString;
        this.hook = paramHook;
        if (paramHook.isBuiltIn()) {
            this.isEnabled = true;
        } else {
            this.isEnabled = (Bukkit.getPluginManager().getPlugin(getName()) != null && Bukkit.getPluginManager().getPlugin(getName()).isEnabled());
        }
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public dk.manaxi.unikpay.plugin.enums.Hook getEnum() {
        return this.hook;
    }
}
