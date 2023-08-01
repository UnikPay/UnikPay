package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.API.Internal;
import dk.manaxi.unikpay.plugin.Main;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffCancelSubscription extends Effect {
    private Expression<Subscription> subscriptions;

    static {
        Skript.registerEffect(EffCancelSubscription.class, "[unikpay] cancel subscription %subscriptions%");
    }

    @Override
    protected void execute(@NotNull Event event) {
        final Subscription[] subscriptions = this.subscriptions.getAll(event);

        if (Main.getAPIKEY() == null) {
            Skript.error("Du mangler at putte din apikey ind i config.yml");
            return;
        }

        for (Subscription subscription : subscriptions) {
            Internal.cancelSubscription(subscription);
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "[unikpay] cancel subscription %subscriptions%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        this.subscriptions = (Expression<Subscription>) expressions[0];
        return true;
    }
}
