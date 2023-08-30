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

public class ExprIdOfSubscription extends SimplePropertyExpression<Subscription, String> {
    static {
        register(ExprIdOfSubscription.class, String.class, "id", "subscription");
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "id";
    }

    @Override
    public @Nullable String convert(Subscription subscription) {
        return subscription.get_id();
    }
}
