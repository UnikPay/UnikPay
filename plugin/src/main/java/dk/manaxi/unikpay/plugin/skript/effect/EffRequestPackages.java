package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.classes.Package;
import dk.manaxi.unikpay.plugin.API.Internal;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffRequestPackages extends Effect {
    private static final String url = Config.MAINURL + "request";

    private Expression<Player> player;
    private Expression<Package> Package;


    static {
        Skript.registerEffect(EffRequestPackages.class, "[unikpay] request %player% packages %packages%");
    }

    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        final Package[] packages = this.Package.getAll(event);

        if (player == null || packages.length == 0)
            return;

        Internal.sendPackageRequest(player, packages);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "[unikpay] request %player% packages %packages%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.Package = (Expression<Package>) expressions[1];
        return true;
    }
}
