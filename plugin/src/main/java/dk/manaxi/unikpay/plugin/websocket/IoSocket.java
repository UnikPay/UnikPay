package dk.manaxi.unikpay.plugin.websocket;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.classes.Betaling;
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

public class IoSocket {
    private static Socket socket;

    public static void connectSocket() {
        System.out.print("connectSocket()");
        try {
            IO.Options options = IO.Options.builder()
                    .setAuth(Collections.singletonMap("token", Main.getAPIKEY()))
                    .build();
            socket = IO.socket("https://unikpay.manaxi.dk", options);
            socket.on(Socket.EVENT_CONNECT, args -> Main.getInstance().getLogger().info("Socket.io connected."));
            socket.on(Socket.EVENT_DISCONNECT, args -> Main.getInstance().getLogger().info("Socket.io disconnected."));

            //when someone accepts on discord
            socket.on("acceptRequest", args -> {

                System.out.print("Jep der sker noget 1");
                try {
                    String ok = Arrays.toString(args);
                    Gson gson = new Gson();
                    JsonArray jsonArray = (JsonArray) args[0];

                    System.out.print("svarOBJ -> " + jsonArray);


                    Type listType = (new TypeToken<List<Betaling>>() {}).getType();
                    System.out.print("listType -> " + listType);

                    final List<Betaling> betalinger = (List<Betaling>) gson.fromJson(jsonArray, listType);
                    Bukkit.broadcastMessage("betalinger -> " + betalinger);

                   

                    JSONObject obj = new JSONObject((args[0].toString()));
                    String uuid = obj.getJSONObject("mcaccount").getString("uuid");

                    Bukkit.broadcastMessage(uuid);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            });

            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
