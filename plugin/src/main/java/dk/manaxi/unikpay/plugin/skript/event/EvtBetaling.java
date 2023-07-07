package dk.manaxi.unikpay.plugin.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.fetch.Payments;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;
import dk.manaxi.unikpay.plugin.skript.classes.id;

import java.lang.reflect.Array;

public class EvtBetaling extends SkriptEvent {

    static {
        Skript.registerEvent("unikpay betaling", EvtBetaling.class, OnBetaling.class, "[on] unikpay betaling");
        /*
        * Handle the player
         */
        EventValues.registerEventValue(OnBetaling.class, OfflinePlayer.class, new Getter<OfflinePlayer, OnBetaling>() {
            public OfflinePlayer get(OnBetaling event) {
                return event.getPlayer();
            }
        }, 0);
        /*
         * Handle the amount
         */
        EventValues.registerEventValue(OnBetaling.class, Number.class, new Getter<Number, OnBetaling>() {
            public Number get(OnBetaling event) {
                return event.getAmount();
            }
        }, 0);

        /*
         * Betaling id'et
         */
        EventValues.registerEventValue(OnBetaling.class, id.class, new Getter<id, OnBetaling>() {
            public id get(OnBetaling event) {
                return event.getId();
            }
        }, 0);

        EventValues.registerEventValue(OnBetaling.class, Pakke[].class, new Getter<Pakke[], OnBetaling>() {
            public Pakke[] get(OnBetaling event) {
                return event.getPakker();
            }
        }, 0);


        EventValues.registerEventValue(OnBetaling.class, Pakke.class, new Getter<Pakke, OnBetaling>() {
            public Pakke get(OnBetaling event) {
                return event.getPakker()[0];
            }
        }, 0);

        (new BukkitRunnable() {
            @Override
            public void run() {
                Payments.fetchPayments();
            }
        }).runTaskTimer(Main.getInstance(), 20L, 600L);
    }


    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "unikpay betaling";
    }
}
