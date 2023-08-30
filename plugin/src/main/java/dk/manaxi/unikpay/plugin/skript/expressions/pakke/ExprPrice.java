package dk.manaxi.unikpay.plugin.skript.expressions.pakke;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExprPrice extends SimplePropertyExpression<Pakke, Float> {
    static {
        register(ExprPrice.class, Float.class, "price", "pakke");
    }

    @NotNull
    @Override
    public Class<? extends Float> getReturnType() {
        return Float.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "price";
    }

    @Override
    public @Nullable Float convert(Pakke pakke) {
        return pakke.getPrice();
    }
}
