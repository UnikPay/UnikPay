package dk.manaxi.unikpay.plugin.skript.expressions.subscription;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.manaxi.unikpay.api.classes.Subscription;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ExprPlayerOfSubscription extends SimplePropertyExpression<Subscription, OfflinePlayer> {
    static {
        register(ExprPlayerOfSubscription.class, OfflinePlayer.class, "player", "subscription");
    }

    @Override
    public @NotNull Class<? extends OfflinePlayer> getReturnType() {
        return OfflinePlayer.class;
    }

    @NotNull
    @Override
    protected String getPropertyName() {
        return "player";
    }

    @Override
    public @Nullable OfflinePlayer convert(Subscription subscription) {
        return Bukkit.getOfflinePlayer(subscription.getMcaccount().getUuid());
    }
}
