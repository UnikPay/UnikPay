package dk.manaxi.unikpay.plugin.skript.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprPrice extends EventValueExpression<Float> {
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

    public ExprPrice() {
        super(Float.class);
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(OnBetaling.class)) {
            Skript.error("The expression 'pakke price' can only be used in unikpay betaling event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
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