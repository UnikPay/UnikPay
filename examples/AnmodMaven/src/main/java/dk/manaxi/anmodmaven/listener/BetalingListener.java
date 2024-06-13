package dk.manaxi.anmodmaven.listener;

import dk.manaxi.unikpay.plugin.API.Internal;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BetalingListener implements Listener {

    @EventHandler
    public void onBetaling(OnBetaling event) {

        if(event.getPakke().getName().equalsIgnoreCase("fly")) {
            Bukkit.broadcastMessage("§8§l[ §e§lBUY§f§lCRAFT §8§l] §f" + event.getPlayer().getName() + " har nu købt fly!");
        }

        Internal.acceptPackageRequest(event.getId().toString());
    }
}
