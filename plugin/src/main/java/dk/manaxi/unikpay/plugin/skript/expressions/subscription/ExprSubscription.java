package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Subscription;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprSubscription extends SimpleExpression<Subscription> {
    static {
        Skript.registerExpression(ExprSubscription.class, Subscription.class, ExpressionType.SIMPLE, "subscription with id %string%");
    }

    private Expression<String> id;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected Subscription @NotNull [] get(@NotNull Event e) {
        return new Subscription[]{new Subscription(id.getSingle(e))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Subscription> getReturnType() {
        return Subscription.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "subscription with id %string%";
    }
}
