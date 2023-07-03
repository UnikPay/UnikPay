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
import org.bukkit.event.Event;
import dk.manaxi.unikpay.plugin.skript.classes.id;

import java.util.HashMap;
import java.util.Map;

public class EffAccept extends Effect {
    private Expression<id> id;

    static {
        Skript.registerEffect(EffAccept.class, "unikpay accepter betaling %id%");
    }

    @Override
    protected void execute(Event e) {
        if(id.getSingle(e) == null) {
            return;
        }

        String url = Config.MAINURL + "request/" + id.getSingle(e) + "/complete";

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                String svar = HttpsClient.sendRequest(url, "POST", "", Main.getAPIKEY(), null);

                JsonObject response = new Gson().fromJson(svar, JsonObject.class);
                boolean success = response.get("success").getAsBoolean();

                if(success) {
                    Main.getInstance().getLogger().info("Accepteret " + id.getSingle(e));
                } else {
                    Skript.error(svar);
                    Main.getInstance().getLogger().info("En fejl opstod: " + svar);
                }
            }
        });
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }
    /*
    protected void execute(Event event) {
    final id _queue = (id)this.queue.getSingle(event);
    if (_queue == null)
      return;
    final JsonObject obj = new JsonObject();
    obj.addProperty("purchase", _queue.toString());
    Bukkit.getScheduler().runTaskAsynchronously((Plugin)SuperPay.instance, new Runnable() {
          public void run() {
            String svar = Utils.put("https://api.superpay.hundeklemmen.com/server/purchase", obj.toString(), SuperPay.authorization);
            if (svar != null)
              if (svar.equalsIgnoreCase("success")) {
                SuperPay.instance.getLogger().info("Accepteret k" + _queue);
              } else {
                Skript.error(svar);
                SuperPay.instance.getLogger().info("En fejl opstod: " + svar);
              }
          }
        });
  }
  */

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.id = (Expression) exprs[0];
        return true;
    }
}