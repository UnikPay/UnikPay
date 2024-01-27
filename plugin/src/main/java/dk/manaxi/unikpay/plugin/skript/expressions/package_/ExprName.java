package dk.manaxi.unikpay.plugin.skript.expressions.package_;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import dk.manaxi.unikpay.api.classes.Package;
import org.jetbrains.annotations.NotNull;

public class ExprName extends SimplePropertyExpression<Package, String> {
    static {
        register(ExprName.class, String.class, "name", "package");
    }

    @NotNull
    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "name";
    }

    @Override
    public String convert(Package Package) {
        return Package.getName();
    }
}