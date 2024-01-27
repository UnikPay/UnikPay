package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.manaxi.unikpay.api.classes.Subscription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class ExprNextPaymentOfSubscription extends SimplePropertyExpression<Subscription, Date> {
    static {
        register(ExprNextPaymentOfSubscription.class, Date.class, "next payment", "subscription");
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
