package dk.manaxi.unikpay.plugin.event;


import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnBetaling extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final OfflinePlayer player;
    private final Package[] packages;
    private final float amount;
    private final AcceptId id;

    private Boolean cancelled = Boolean.FALSE;

    public OnBetaling(OfflinePlayer player, Package[] packages, float amount, AcceptId id) {
        this.player = player;
        this.packages = packages;
        this.amount = amount;
        this.id = id;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.player.getUniqueId());
    }

    public Package[] getPackages() {
        return packages;
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

    public AcceptId getId() {
        return id;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
