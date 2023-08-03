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

import java.util.Objects;

public class ExprDurationTypeOfSubscription extends SimpleExpression<String> {
    private Expression<Subscription> subscription;
    static {
        Skript.registerExpression(ExprDurationTypeOfSubscription.class, String.class, ExpressionType.SIMPLE, "[the] duration type of %subscription%");
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final @NotNull Kleenean isDelayed, @NotNull final SkriptParser.ParseResult parser) {
        subscription = (Expression<Subscription>) exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(final @Nullable Event e, final boolean debug) {
        return "[the] duration type of %subscription%";
    }

    @NotNull
    @Override
    protected String[] get(@NotNull Event e) {
        return new String[]{Objects.requireNonNull(subscription.getSingle(e)).getDurationType()};
    }
}
