package dk.manaxi.unikpay.plugin.skript.expressions.pakke;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ExprId extends EventValueExpression<String> {
    private Expression<Pakke> pakke;
    static {
        Skript.registerExpression(ExprId.class, String.class, ExpressionType.SIMPLE, "[the] id of %pakke%");
    }

    @NotNull
    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public ExprId() {
        super(String.class);
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, @NotNull final Kleenean isDelayed, @NotNull final SkriptParser.ParseResult parser) {
        pakke = (Expression<Pakke>) exprs[0];
        return true;
    }

    @NotNull
    @Override
    public String toString(final @Nullable Event e, final boolean debug) {
        return "[the] id of %pakke%";
    }

    @NotNull
    @Override
    protected String[] get(@NotNull Event e) {
        return new String[]{Objects.requireNonNull(pakke.getSingle(e)).getId()};
    }
}
