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

public class ExprDurationTypeOfSubscription extends SimplePropertyExpression<Subscription, String> {
    static {
        register(ExprDurationTypeOfSubscription.class, String.class, "duration type", "subscription");
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "duration type";
    }

    @Override
    public @Nullable String convert(Subscription subscription) {
        return subscription.getDurationType();
    }
}
