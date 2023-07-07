package dk.manaxi.unikpay.plugin.event;


import dk.manaxi.unikpay.api.classes.Pakke;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import dk.manaxi.unikpay.plugin.skript.classes.id;

public class OnBetaling extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private OfflinePlayer player;
    private Pakke[] pakker;
    private float amount;
    private id id;

    private Boolean cancelled = Boolean.FALSE;

    public OnBetaling(OfflinePlayer player, Pakke[] pakker, float amount, id id) {
        this.player = player;
        this.pakker = pakker;
        this.amount = amount;
        this.id = id;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.player.getUniqueId());
    }

    public Pakke[] getPakker() {
        return pakker;
    }

    public Pakke getPakke() {
        return pakker[0];
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

    public id getId() {
        return id;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
