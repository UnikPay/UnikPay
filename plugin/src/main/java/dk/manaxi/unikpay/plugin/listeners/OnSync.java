package dk.manaxi.unikpay.plugin.listeners;

import com.google.gson.JsonObject;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.websocket.IoSocket;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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
                Main.getInstance().getLang().send(event.getPlayer(), "unikpay.status.update", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
            }
        });

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("uuid", event.getPlayer().getUniqueId().toString());
            jsonObject.addProperty("username", event.getPlayer().getName());
            IoSocket.getSocket().emit("playerLeave", jsonObject);
        });
    }
}
