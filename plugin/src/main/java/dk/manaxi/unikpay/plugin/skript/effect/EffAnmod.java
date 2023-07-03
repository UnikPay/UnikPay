package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;

public class EffAnmod extends Effect {
    private Expression<Player> player;

    private Expression<Number> amount;

    private Expression<JsonArray> pakke;

    static {
        Skript.registerEffect(EffAnmod.class, "unikpay anmod %player% om %number% emeralder for %string%");
    }
    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        Number amount = this.amount.getSingle(event);
        final JsonArray pakke = this.pakke.getSingle(event);

        if (player == null || pakke == null)
            return;

        final JsonObject obj = new JsonObject();
        obj.addProperty("uuid", player.getUniqueId().toString());
        obj.addProperty("amount", amount);
        obj.addProperty("pakke", String.valueOf(pakke));


    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        this.player = (Expression)expressions[0];
        this.amount = (Expression)expressions[1];
        this.pakke = (Expression)expressions[2];
        return true;
    }
}
