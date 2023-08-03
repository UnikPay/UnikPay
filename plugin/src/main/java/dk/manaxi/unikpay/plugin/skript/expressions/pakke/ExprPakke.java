package dk.manaxi.unikpay.plugin.skript.expressions.pakke;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import dk.manaxi.unikpay.api.classes.Pakke;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ExprPakke extends SimpleExpression<Pakke> {
    static {
        Skript.registerExpression(ExprPakke.class, Pakke.class, ExpressionType.COMBINED, "[the] (pakke|package) (named|navngivet) %string% for %number% em[(eralds|s|eralder|erald)][ (med id|with id) %-string%] ");
    }

    private Expression<String> NAME;
    private Expression<Number> PRICE;
    private Expression<String> ID;

    @Override
    public @NotNull Class<? extends Pakke> getReturnType() {
        return Pakke.class;
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
        return "[the] (pakke|package) (named|navngivet) %string% for %number% em(eralds|s|eralder)[ (med id|with id) %-string%]";
    }

    @NotNull
    @Override
    protected Pakke[] get(@NotNull Event e) {
        if(ID != null) {
            return new Pakke[]{new Pakke(Objects.requireNonNull(PRICE.getSingle(e)).floatValue(), NAME.getSingle(e), ID.getSingle(e))};
        }

        return new Pakke[]{new Pakke(Objects.requireNonNull(PRICE.getSingle(e)).floatValue(), NAME.getSingle(e), NAME.getSingle(e))};
    }

    @NotNull
    public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
        return (Class[]) CollectionUtils.array((Object[])new Class[0]);
    }

    @Override
    public boolean isLoopOf(String string) {
        return string.equals("pakke");
    }
}
