package dk.manaxi.unikpay.plugin.listeners;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            if (UpdateManager.isANewVersionAvailable(Main.getInstance().getDescription().getVersion())) {
                event.getPlayer().sendMessage(Config.get("prefix")[0]);
                event.getPlayer().sendMessage(ColorUtils.getColored(" &fDer er en ny version af &aUnikpay.Jar"));
                event.getPlayer().sendMessage(ColorUtils.getColored(" &fBenyt dig af &a/unikpay update"));
            }
        }
    }
}
