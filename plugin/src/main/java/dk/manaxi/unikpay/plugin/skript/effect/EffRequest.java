package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.plugin.API.Internal;
import dk.manaxi.unikpay.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffRequest extends Effect {
    private Expression<Player> player;
    private Expression<Number> amount;
    private Expression<String> Package;
    private Expression<String> id;


    static {
        Skript.registerEffect(EffRequest.class, "[unikpay] request %player% %number% em[(eralds|s)] for %string%[ with id %-string%]");
    }

    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        final Number amount = this.amount.getSingle(event);
        final String Package = this.Package.getSingle(event);
        final String id = this.id != null ? this.id.getSingle(event) : Package;

        if (Main.getAPIKEY() == null) {
            Skript.error("Du mangler at putte din apikey ind i config.yml");
            return;
        }

        if (player == null || Package == null || amount == null)
            return;

        Internal.sendPackageRequest(player, new Package[]{new Package(amount.floatValue(), Package, id)});
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "[unikpay] request %player% %number% em[(eralds|s)] for %string%[ with id %-string%]";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.amount = (Expression<Number>) expressions[1];
        this.Package = (Expression<String>) expressions[2];
        this.id = (Expression<String>) expressions[3];
        return true;
    }
}
