package dk.manaxi.unikpay.plugin.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprEventId extends SimpleExpression<AcceptId> {
    static {
        Skript.registerExpression(ExprEventId.class, AcceptId.class, ExpressionType.SIMPLE, "event-id");
    }

    @Override
    public boolean init(@NotNull Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        return true;
    }

    @NotNull
    @Override
    protected AcceptId[] get(@NotNull Event e) {
        if (!(e instanceof OnBetaling)) {
            return new AcceptId[]{};
        }

        return new AcceptId[]{((OnBetaling) e).getId()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends AcceptId> getReturnType() {
        return AcceptId.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "event-id";
    }


}
