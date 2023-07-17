package dk.manaxi.unikpay.plugin.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.event.OnPayRequest;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EvtPayRequest extends SkriptEvent {
    static {
        Skript.registerEvent("unikpay pay request", EvtPayRequest.class, OnPayRequest.class, "[on] unikpay pay request");
        /*
         * Handle the player
         */
        EventValues.registerEventValue(OnPayRequest.class, OfflinePlayer.class, new Getter<OfflinePlayer, OnPayRequest>() {
            public OfflinePlayer get(OnPayRequest event) {
                return event.getPlayer();
            }
        }, 0);
        /*
         * Handle the amount
         */
        EventValues.registerEventValue(OnPayRequest.class, Number.class, new Getter<Number, OnPayRequest>() {
            public Number get(OnPayRequest event) {
                return event.getAmount();
            }
        }, 0);
        EventValues.registerEventValue(OnPayRequest.class, String.class, new Getter<String, OnPayRequest>() {
            public String get(OnPayRequest event) {
                return event.getMessage();
            }
        }, 0);
        EventValues.registerEventValue(OnPayRequest.class, Boolean.class, new Getter<Boolean, OnPayRequest>() {
            public Boolean get(OnPayRequest event) {
                return event.getSuccess();
            }
        }, 0);

    }

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "unikpay pay";
    }
}
