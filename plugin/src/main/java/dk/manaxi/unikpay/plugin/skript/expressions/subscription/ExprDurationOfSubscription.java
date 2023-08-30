package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
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

public class ExprDurationOfSubscription extends SimplePropertyExpression<Subscription, Number> {
    static {
        register(ExprDurationOfSubscription.class, Number.class, "duration", "subscription");
    }

    @Override
    public @NotNull Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "duration";
    }

    @Override
    public @Nullable Number convert(Subscription subscription) {
        return subscription.getDuration();
    }
}
