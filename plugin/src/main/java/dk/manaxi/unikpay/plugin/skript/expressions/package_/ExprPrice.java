package dk.manaxi.unikpay.plugin.skript.expressions.package_;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.manaxi.unikpay.api.classes.Package;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprPrice extends SimplePropertyExpression<Package, Float> {
    static {
        register(ExprPrice.class, Float.class, "price", "package");
    }

    @NotNull
    @Override
    public Class<? extends Float> getReturnType() {
        return Float.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "price";
    }

    @Override
    public @Nullable Float convert(Package Package) {
        return Package.getPrice();
    }
}
