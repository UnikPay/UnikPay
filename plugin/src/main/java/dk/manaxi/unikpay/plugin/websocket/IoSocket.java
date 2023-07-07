package dk.manaxi.unikpay.plugin.websocket;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.classes.Betaling;
import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.skript.classes.id;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.bukkit.Bukkit;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class IoSocket {
    private static Socket socket;

    public static void connectSocket() {
        try {
            IO.Options options = IO.Options.builder()
                    .setAuth(Collections.singletonMap("token", Main.getAPIKEY()))
                    .build();
            socket = IO.socket("https://unikpay.manaxi.dk", options);
            socket.on(Socket.EVENT_CONNECT, args -> Main.getInstance().getLogger().info("Socket.io connected."));
            socket.on(Socket.EVENT_DISCONNECT, args -> Main.getInstance().getLogger().info("Socket.io disconnected."));

            //when someone accepts on discord
            socket.on("acceptRequest", args -> {
                String ok = Arrays.toString(args);
                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(ok, JsonArray.class);

                JsonObject obj = jsonArray.get(0).getAsJsonObject();
                UUID uuid = UUID.fromString(obj.getAsJsonObject("mcaccount").get("uuid").getAsString());
                Type listType = (new TypeToken<List<Pakke>>() {}).getType();
                final List<Pakke> pakker = (List<Pakke>) gson.fromJson(obj.getAsJsonArray("requests"), listType);

                Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getPluginManager().callEvent(new OnBetaling(
                                Bukkit.getOfflinePlayer(uuid),
                                (Pakke[]) pakker.toArray(),
                                obj.get("amount").getAsFloat(),
                                new id(obj.get("_id").getAsString())
                        ));
                    }
                });
            });
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return socket;
    }

}
