package dk.manaxi.unikpay.plugin.skript.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffAccept extends Effect {
    private Expression<AcceptId> id;

    static {
        Skript.registerEffect(EffAccept.class, "unikpay accepter betaling %id%");
    }

    @Override
    protected void execute(@NotNull Event e) {
        if(id.getSingle(e) == null) {
            return;
        }

        String url = Config.MAINURL + "request/" + id.getSingle(e) + "/complete";

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.sendRequest(url, "POST", "", Main.getAPIKEY(), null);

            JsonObject response = new Gson().fromJson(svar, JsonObject.class);
            boolean success = response.get("success").getAsBoolean();

            if(success) {
                Main.getInstance().getLogger().info("Accepteret " + id.getSingle(e));
            } else {
                Skript.error(svar);
                Main.getInstance().getLogger().info("En fejl opstod: " + svar);
            }
        });
    }

    @NotNull
    @Override
    public String toString(Event e, boolean debug) {
        return "unikpay accepter betaling %id%";
    }
    

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.id = (Expression<AcceptId>) exprs[0];
        return true;
    }
}
