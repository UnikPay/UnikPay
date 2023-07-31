package dk.manaxi.unikpay.plugin.manager;

import ch.njol.skript.Skript;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.api.classes.DurationType;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnPayRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.List;

public class RequestManager {

    public static void sendPackageRequest(Player player, String pakke, Number price, String id) {
        sendPackageRequest(player, new Pakke[]{new Pakke(price.floatValue(), pakke, id)});
    }

    public static void sendPackageRequest(Player player, Pakke[] packages) {
        String url = Config.MAINURL + "request";
        JsonObject payload = new JsonObject();
        payload.addProperty("uuid", player.getUniqueId().toString());
        JsonArray pakker = new JsonArray();

        for (Pakke pakke : packages) {
            pakker.add(pakke.toJSON());
        }

        payload.add("pakker", pakker);

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.sendRequest(url, "POST", payload.toString(), Main.getAPIKEY(), null);
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

    public static JsonObject sendSubscriptionRequest(Player player, Pakke pakke, Number duration, DurationType type) {
        String url = Config.MAINURL + "subscription";
        JsonObject payload = new JsonObject();
        payload.addProperty("uuid", player.getUniqueId().toString());
        payload.add("pakke", pakke.toJSON());
        payload.addProperty("duration", duration);
        payload.addProperty("durationType", type.shortName);

        String svar = HttpsClient.sendRequest(url, "POST", payload.toString(), Main.getAPIKEY(), null);
        if (svar == null) return null;

        JsonObject response = new Gson().fromJson(svar, JsonObject.class);
        String message = response.get("message").getAsString();

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            if(message.trim().equalsIgnoreCase(Config.IKKELINKET_MESSAGE)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ikkelinket");
            } else if(message.trim().equalsIgnoreCase(Config.IKKEMC)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ikkelinket");
            } else if (message.equalsIgnoreCase(Config.ACCEPTERE_KOEBET)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "accepterbetaling");
            } else if (message.equalsIgnoreCase(Config.RATELIMIT)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ratetime");
            } else if (message.equalsIgnoreCase(Config.ALLEREDE_SUB)) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "allerede_sub");
            }
        });

        return response;
    }

    public static List<Subscription> getSubscriptionsRequest() {
        String url = Config.MAINURL + "subscription";
        Gson gson = new Gson();

        String svar = HttpsClient.getRequest(url, Main.getAPIKEY());
        if(svar == null) return null;

        JsonObject response = new Gson().fromJson(svar, JsonObject.class);
        Type listType = (new TypeToken<List<Subscription>>() {}).getType();
        return gson.fromJson(response.getAsJsonArray("subscriptions"), listType);
    }

    public static JsonObject cancelSubscription(Subscription subscription) {
        String url = Config.MAINURL + "subscription/" + subscription.get_id() + "/cancel";
        String svar = HttpsClient.sendRequest(url, "POST", "", Main.getAPIKEY(), null);
        if (svar == null) return null;

        JsonObject response = new Gson().fromJson(svar, JsonObject.class);
        return response;
    }

    public static void sendPayRequest(Player player, String name, Number amount) {
        String url = Config.MAINURL + "pay";

        JsonObject payload = new JsonObject();
        payload.addProperty("uuid", player.getUniqueId().toString());
        payload.addProperty("amount", amount);
        payload.addProperty("name", name);

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.sendRequest(url, "POST", payload.toString(), Main.getAPIKEY(), null);

            JsonObject response = new Gson().fromJson(svar, JsonObject.class);
            String message = response.get("message").getAsString();
            boolean success = response.get("success").getAsBoolean();

            if(success) {
                dk.manaxi.unikpay.plugin.configuration.Config.send(player, "paysuccess");
            } else {
                if(message.trim().equalsIgnoreCase(Config.IKKELINKET_MESSAGE)) {
                    dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ikkelinket");
                } else if(message.trim().equalsIgnoreCase(Config.IKKEMC)) {
                    dk.manaxi.unikpay.plugin.configuration.Config.send(player, "ikkelinket");
                }
                Skript.error(svar);
                Main.getInstance().getLogger().info("En fejl opstod: " + svar);
            }
            Bukkit.getPluginManager().callEvent(new OnPayRequest(player, name, amount.floatValue(), success, message));
        });
    }

    public static void acceptPackageReqeust(String id) {

        String url = Config.MAINURL + "request/" + id + "/complete";

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.sendRequest(url, "POST", "", Main.getAPIKEY(), null);

            JsonObject response = new Gson().fromJson(svar, JsonObject.class);
            boolean success = response.get("success").getAsBoolean();

            if(success) {
                Main.getInstance().getLogger().info("Accepteret " + id);
            } else {
                Skript.error(svar);
                Main.getInstance().getLogger().info("En fejl opstod: " + svar);
            }
        });
    }

}
