package dk.manaxi.unikpay.plugin.manager;

import ch.njol.skript.Skript;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class RequestManager {

    @SuppressWarnings("unchecked")
    public static void sendPackageRequest(Player player, String pakke, int price, String id) {

        String url = Config.MAINURL + "request";

        JSONObject payload = new JSONObject();
        payload.put("uuid", player.getUniqueId().toString());

        JSONObject pakkerObj = new JSONObject();
        pakkerObj.put("name", pakke);
        pakkerObj.put("id", id != null ? id : pakke);
        pakkerObj.put("price", price);

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

    public static void acceptPackageReqeust(String id) {

        String url = Config.MAINURL + "request/" + id + "/complete";

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                String svar = HttpsClient.sendRequest(url, "POST", "", Main.getAPIKEY(), null);

                JsonObject response = new Gson().fromJson(svar, JsonObject.class);
                boolean success = response.get("success").getAsBoolean();

                if(success) {
                    Main.getInstance().getLogger().info("Accepteret " + id);
                } else {
                    Skript.error(svar);
                    Main.getInstance().getLogger().info("En fejl opstod: " + svar);
                }
            }
        });

    }

}
