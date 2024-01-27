package dk.manaxi.unikpay.plugin.skript.expressions.package_;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import dk.manaxi.unikpay.api.classes.Package;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ExprPackage extends SimpleExpression<Package> {
    static {
        Skript.registerExpression(ExprPackage.class, Package.class, ExpressionType.COMBINED, "[the] package named %string% for %number% em[(eralds|s)][ with id %-string%] ");
    }

    private Expression<String> NAME;
    private Expression<Number> PRICE;
    private Expression<String> ID;

    @Override
    public @NotNull Class<? extends Package> getReturnType() {
        return Package.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }



    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parser) {
        NAME = (Expression<String>) e[0];
        PRICE = (Expression<Number>) e[1];
        ID = (Expression<String>) e[2];
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean arg1) {
        return "[the] package named %string% for %number% em[(eralds|s)][ with id %-string%] ";
    }

    @NotNull
    @Override
    protected Package[] get(@NotNull Event e) {
        if(ID != null) {
            return new Package[]{new Package(Objects.requireNonNull(PRICE.getSingle(e)).floatValue(), NAME.getSingle(e), ID.getSingle(e))};
        }

        return new Package[]{new Package(Objects.requireNonNull(PRICE.getSingle(e)).floatValue(), NAME.getSingle(e), NAME.getSingle(e))};
    }

    @NotNull
    public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
        return (Class[]) CollectionUtils.array((Object[])new Class[0]);
    }

    @Override
    public boolean isLoopOf(String string) {
        return string.equals("package");
    }
}
