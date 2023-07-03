package dk.manaxi.unikpay.plugin.event;


import dk.manaxi.unikpay.api.classes.Betaling;
import dk.manaxi.unikpay.api.classes.Pakke;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Array;
import dk.manaxi.unikpay.plugin.skript.classes.id;

public class OnBetaling extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private OfflinePlayer player;
    private Array pakke;

    private float amount;

    private Boolean cancelled = Boolean.FALSE;

    public OnBetaling(OfflinePlayer player, Array pakke, float amount, Betaling betal) {
        this.player = player;
        this.pakke = pakke;
        this.amount = amount;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.player.getUniqueId());
    }

    public Array getPakke() {
        return pakke;
    }

    public float getAmount() {
        return amount;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
