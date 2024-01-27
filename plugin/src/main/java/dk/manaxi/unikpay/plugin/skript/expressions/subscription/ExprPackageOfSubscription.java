package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.api.classes.Subscription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprPackageOfSubscription extends SimplePropertyExpression<Subscription, Package> {
    static {
        register(ExprPackageOfSubscription.class, Package.class, "package", "subscription");
    }

    @Override
    public @NotNull Class<? extends Package> getReturnType() {
        return Package.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "package";
    }

    @Override
    public @Nullable Package convert(Subscription subscription) {
        return subscription.getPackage();
    }
}
