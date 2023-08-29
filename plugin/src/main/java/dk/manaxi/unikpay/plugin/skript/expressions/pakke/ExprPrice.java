package dk.manaxi.unikpay.plugin.skript.expressions.pakke;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprPrice extends SimpleExpression<Float> {
    private Expression<Pakke> pakke;
    static {
        Skript.registerExpression(ExprPrice.class, Float.class, ExpressionType.SIMPLE, "[the] price of %pakke%");
    }

    @Override
    public Class<? extends Float> getReturnType() {
        return Float.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        pakke = (Expression<Pakke>) exprs[0];
        return true;
    }

    @Override
    public String toString(final @Nullable Event e, final boolean debug) {
        return "[the] price of %pakke%";
    }

    @Override
    @Nullable
    protected Float[] get(Event e) {
        return new Float[]{pakke.getSingle(e).getPrice()};
    }
}
