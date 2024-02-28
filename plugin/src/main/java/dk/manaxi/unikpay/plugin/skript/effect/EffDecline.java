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

public class EffDecline extends Effect {
    private Expression<AcceptId> id;
    private Expression<String> reason;

    static {
        Skript.registerEffect(EffDecline.class, "unikpay decline purchase %unikid% with reason %string%");
    }

    @Override
    protected void execute(@NotNull Event e) {
        if(id.getSingle(e) == null || reason.getSingle(e) == null) {
            return;
        }

        String url = Main.getInstance().getConfigSystem().getUrl() + "/v1/request/" + id.getSingle(e) + "/decline";

        JsonObject payload = new JsonObject();
        payload.addProperty("reason", reason.getSingle(e));
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.sendRequest(url, "POST", payload.toString(), Main.getAPIKEY(), null);

            JsonObject response = new Gson().fromJson(svar, JsonObject.class);
            boolean success = response.get("success").getAsBoolean();

            if(success) {
                Main.getInstance().getLogger().info("Afvis " + id.getSingle(e));
            } else {
                Skript.error(svar);
                Main.getInstance().getLogger().info("En fejl opstod: " + svar);
            }
        });
    }

    @NotNull
    @Override
    public String toString(Event e, boolean debug) {
        return "unikpay decline purchase %unikid% with reason %string%";
    }
    

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.id = (Expression<AcceptId>) exprs[0];
        this.reason = (Expression<String>) exprs[1];
        return true;
    }
}
