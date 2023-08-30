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

import java.util.Date;
import java.util.Objects;

public class ExprnextPaymentOfSubscription extends SimplePropertyExpression<Subscription, Date> {
    static {
        register(ExprnextPaymentOfSubscription.class, Date.class, "next payment", "subscription");
    }

    @Override
    public @NotNull Class<? extends Date> getReturnType() {
        return Date.class;
    }


    @NotNull
    @Override
    protected String getPropertyName() {
        return "next payment";
    }

    @Override
    public @Nullable Date convert(Subscription subscription) {
        return subscription.getNextPayment();
    }
}
