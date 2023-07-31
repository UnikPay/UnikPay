package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.event.OnSubscriptionPayment;
import dk.manaxi.unikpay.plugin.skript.expressions.pakke.ExprId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ExprPlayerOfSubscription extends SimpleExpression<OfflinePlayer> {
    private Expression<Subscription> subscription;
    static {
        Skript.registerExpression(ExprPlayerOfSubscription.class, OfflinePlayer.class, ExpressionType.SIMPLE, "[the] player of %subscription%");
    }

    @Override
    public @NotNull Class<? extends OfflinePlayer> getReturnType() {
        return OfflinePlayer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final @NotNull Kleenean isDelayed, final SkriptParser.@NotNull ParseResult parser) {
        subscription = (Expression<Subscription>) exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(final @Nullable Event e, final boolean debug) {
        return "[the] player of %subscription%";
    }

    @Override
    protected OfflinePlayer @NotNull [] get(@NotNull Event e) {
        return new OfflinePlayer[]{Bukkit.getOfflinePlayer(Objects.requireNonNull(subscription.getSingle(e)).getMcaccount().getUuid())};
    }
}
