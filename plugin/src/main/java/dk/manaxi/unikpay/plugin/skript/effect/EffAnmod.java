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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class EffAnmod extends Effect {
    private static String url = Config.MAINURL + "request";

    private Expression<Player> player;
    private Expression<Number> amount;
    private Expression<String> pakke;
    private Expression<String> id;


    static {
        Skript.registerEffect(EffAnmod.class, "unikpay (request|anmod) %player% [om ] %number% em(eralds|s|eralder) for %string%[ med id %-string%]");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        final Number amount = this.amount.getSingle(event);
        final String pakke = this.pakke.getSingle(event);
        final String id = this.id != null ? this.id.getSingle(event) : null;

        if (Main.getAPIKEY() == null) {
            Skript.error("Du mangler at putte din apikey ind i config.yml");
            return;
        }

        if (player == null || pakke == null)
            return;

        JSONObject payload = new JSONObject();
        payload.put("uuid", player.getUniqueId().toString());

        JSONObject pakkerObj = new JSONObject();
        pakkerObj.put("name", pakke);
        pakkerObj.put("id", id != null ? id : pakke);
        pakkerObj.put("price", amount);

        JSONArray pakker = new JSONArray();
        pakker.add(pakkerObj);

        payload.put("pakker", pakker);


        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.sendRequest(url, "POST", payload.toJSONString(), Main.getAPIKEY(), null);
            if (svar == null) return;

            JsonObject response = new Gson().fromJson(svar, JsonObject.class);
            String message = response.get("message").getAsString();

            if(message.trim().equalsIgnoreCase(Config.IKKELINKET_MESSAGE)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ikkelinket");
            } else if(message.trim().equalsIgnoreCase(Config.IKKEMC)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ikkelinket");
            } else if (message.equalsIgnoreCase(Config.ACCEPTERE_KOEBET)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "accepterbetaling");
            } else if (message.equalsIgnoreCase(Config.RATELIMIT)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ratetime");
            }
        });


    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        this.player = (Expression) expressions[0];
        this.amount = (Expression) expressions[1];
        this.pakke = (Expression) expressions[2];
        this.id = (Expression) expressions[3];
        return true;
    }
}
