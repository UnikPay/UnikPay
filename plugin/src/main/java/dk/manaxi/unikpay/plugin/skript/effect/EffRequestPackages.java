package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.API.Internal;
import dk.manaxi.unikpay.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffRequestPackages extends Effect {
    private static final String url = Main.getInstance().getConfigSystem().getUrl() + "/v1/request";

    private Expression<Player> player;
    private Expression<Pakke> pakke;


    static {
        Skript.registerEffect(EffRequestPackages.class, "[unikpay] (request|anmod) %player% (pakker|packages) %pakkes%");
    }

    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        final Pakke[] pakker = this.pakke.getAll(event);

        if (player == null || pakke == null)
            return;

        Internal.sendPackageRequest(player, pakker);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "[unikpay] (request|anmod) %player% (pakker|packages) %pakkes%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.pakke = (Expression<Pakke>) expressions[1];
        return true;
    }
}
