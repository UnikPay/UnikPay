package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.DurationType;
import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.plugin.API.Internal;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffRequestSubscription extends Effect {
    private Expression<Player> player;
    private Expression<Package> Package;
    private Expression<Number> duration;


    static {
        Skript.registerEffect(EffRequestSubscription.class, "[unikpay] request %player% package %package% in %number% days");
    }

    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        final Package Package = this.Package.getSingle(event);
        final Number duration = this.duration.getSingle(event);

        if (player == null || Package == null)
            return;

        Internal.sendSubscriptionRequest(player, Package, duration, DurationType.DAY);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "[unikpay] request %player% package %package% in %number% days";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.Package = (Expression<Package>) expressions[1];
        this.duration = (Expression<Number>) expressions[2];
        return true;
    }
}

