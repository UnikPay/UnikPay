package dk.manaxi.unikpay.plugin.skript.expressions.package_;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprPackages extends SimpleExpression<Package> {
    static {
        Skript.registerExpression(ExprPackages.class, Package.class, ExpressionType.SIMPLE, "(packages|package)");
    }

    @Override
    public boolean init(@NotNull Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        return true;
    }

    @NotNull
    @Override
    protected Package[] get(@NotNull Event e) {
        if (!(e instanceof OnBetaling)) {
            return new Package[]{};
        }

        return ((OnBetaling) e).getPackages();
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public @NotNull Class<? extends Package> getReturnType() {
        return Package.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "the packages";
    }


}
