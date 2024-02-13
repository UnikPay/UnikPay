package dk.manaxi.unikpay.plugin.listeners;

import com.google.gson.JsonObject;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import dk.manaxi.unikpay.plugin.websocket.IoSocket;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnSync implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("uuid", event.getPlayer().getUniqueId().toString());
            jsonObject.addProperty("username", event.getPlayer().getName());
            IoSocket.getSocket().emit("playerJoin", jsonObject);
            if (event.getPlayer().isOp() && UpdateManager.isANewVersionAvailable(Main.getInstance().getDescription().getVersion()))  {
                event.getPlayer().sendMessage(Config.get("prefix")[0]);
                event.getPlayer().sendMessage(ColorUtils.getColored(" &fDer er en ny version af &aUnikpay.Jar"));
                event.getPlayer().sendMessage(ColorUtils.getColored(" &fBenyt dig af &a/unikpay update"));
            }
        });

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", event.getPlayer().getUniqueId().toString());
        jsonObject.addProperty("username", event.getPlayer().getName());
        IoSocket.getSocket().emit("playerLeave", jsonObject);
    }
}
