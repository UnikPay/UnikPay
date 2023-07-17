package dk.manaxi.unikpay.plugin.event;

import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnPayRequest extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final OfflinePlayer player;
    private final String name;
    private final float amount;
    private final boolean success;
    private final String message;
    private boolean cancelled = false;
    public OnPayRequest(OfflinePlayer player, String name, float amount, boolean success, String message) {
        this.player = player;
        this.name = name;
        this.amount = amount;
        this.success = success;
        this.message = message;
    }
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.player.getUniqueId());
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
