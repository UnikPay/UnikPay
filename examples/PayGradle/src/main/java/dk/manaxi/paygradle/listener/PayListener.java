package dk.manaxi.paygradle.listener;

import dk.manaxi.unikpay.plugin.API.Internal;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.event.OnPayRequest;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PayListener implements Listener {

    @EventHandler
    public void onPay(OnPayRequest event) {
        if (!event.getSuccess()) {
            System.out.println("Payment failed");
            // Tilføj dem tilbage eller logge dem i en database om at det er sket en fejl, for at manuel tjekke det
        } else {
            System.out.println("Payment success");
        }
    }
}
