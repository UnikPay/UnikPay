package dk.manaxi.unikpay.plugin.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.event.OnSubscriptionPayment;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EvtSubscriptionPayment extends SkriptEvent {

    static {
        Skript.registerEvent("unikpay betaling", EvtSubscriptionPayment.class, OnSubscriptionPayment.class, "[on] unikpay subscription payment");
        /*
         * Handle the player
         */
        EventValues.registerEventValue(OnSubscriptionPayment.class, OfflinePlayer.class, new Getter<OfflinePlayer, OnSubscriptionPayment>() {
            public OfflinePlayer get(OnSubscriptionPayment event) {
                return event.getPlayer();
            }
        }, 0);
        /*
         * Handle the amount
         */
        EventValues.registerEventValue(OnSubscriptionPayment.class, Number.class, new Getter<Number, OnSubscriptionPayment>() {
            public Number get(OnSubscriptionPayment event) {
                return event.getAmount();
            }
        }, 0);

        /*
         * Betaling id'et
         */
        EventValues.registerEventValue(OnSubscriptionPayment.class, AcceptId.class, new Getter<AcceptId, OnSubscriptionPayment>() {
            public AcceptId get(OnSubscriptionPayment event) {
                return event.getId();
            }
        }, 0);

        EventValues.registerEventValue(OnSubscriptionPayment.class, Pakke[].class, new Getter<Pakke[], OnSubscriptionPayment>() {
            public Pakke[] get(OnSubscriptionPayment event) {
                return event.getPakker();
            }
        }, 0);


        EventValues.registerEventValue(OnSubscriptionPayment.class, Pakke.class, new Getter<Pakke, OnSubscriptionPayment>() {
            public Pakke get(OnSubscriptionPayment event) {
                return event.getPakker()[0];
            }
        }, 0);


        EventValues.registerEventValue(OnSubscriptionPayment.class, Subscription.class, new Getter<Subscription, OnSubscriptionPayment>() {
            public Subscription get(OnSubscriptionPayment event) {
                return event.getSub();
            }
        }, 0);
    }


    @Override
    public boolean init(@NotNull Literal<?>[] args, int matchedPattern, @NotNull SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return true;
    }

    @NotNull
    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "unikpay subscription payment";
    }
}
