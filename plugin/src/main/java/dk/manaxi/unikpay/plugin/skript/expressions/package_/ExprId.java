package dk.manaxi.unikpay.plugin.skript.expressions.package_;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.manaxi.unikpay.api.classes.Package;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprId extends SimplePropertyExpression<Package, String> {
    static {
        register(ExprId.class, String.class, "id", "package");
    }

    @NotNull
    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "id";
    }

    @Override
    public @Nullable String convert(Package Package) {
        return Package.getId();
    }
}
