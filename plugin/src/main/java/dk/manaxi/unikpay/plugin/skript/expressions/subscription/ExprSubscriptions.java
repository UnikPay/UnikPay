package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.API.Internal;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.skript.expressions.pakke.ExprPakkes;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprSubscriptions extends SimpleExpression<Subscription> {
    static {
        Skript.registerExpression(ExprSubscriptions.class, Subscription.class, ExpressionType.SIMPLE, "all subscriptions");
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    protected Subscription @NotNull [] get(@NotNull Event e) {
        return Internal.getSubscriptionsRequest().toArray(new Subscription[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends Subscription> getReturnType() {
        return Subscription.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "the subscriptions";
    }
}