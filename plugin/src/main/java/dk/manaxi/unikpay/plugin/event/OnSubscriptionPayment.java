package dk.manaxi.unikpay.plugin.event;


import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnSubscriptionPayment extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final OfflinePlayer player;
    private final Package[] packages;
    private final float amount;
    private final Subscription sub;
    private final AcceptId id;
    private Boolean cancelled = Boolean.FALSE;

    public OnSubscriptionPayment(OfflinePlayer player, Package[] packages, float amount, Subscription sub, AcceptId id) {
        this.player = player;
        this.packages = packages;
        this.amount = amount;
        this.sub = sub;
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
    public Subscription getSub() {
        return sub;
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
