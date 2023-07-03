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

public class ExprName extends EventValueExpression<String> {
    private Expression<Pakke> pakke;
    static {
        Skript.registerExpression(ExprName.class, String.class, ExpressionType.SIMPLE, "[the] name of %pakke%");
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    public ExprName() {
        super(String.class);
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(OnBetaling.class)) {
            Skript.error("The expression 'name' can only be used in unikpay betaling event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        pakke = (Expression<Pakke>) exprs[0];
        return true;
    }

    @Override
    public String toString(final @Nullable Event e, final boolean debug) {
        return "[the] name of %pakke%";
    }

    @Override
    @Nullable
    protected String[] get(Event e) {
        return new String[]{pakke.getSingle(e).getName()};
    }
}