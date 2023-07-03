package dk.manaxi.unikpay.plugin.fetch;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.api.classes.Betaling;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Payments {
    private static long lastPaymentFetch = 0L;
    private static String url = Config.MAINURL + "request";

    public static void fetchPayments() {
        if (lastPaymentFetch > (new Date()).getTime() - 5000L)
            return;
        lastPaymentFetch = (new Date()).getTime();
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            public void run() {
                String svar = HttpsClient.get(url, Main.getAPIKEY());
                System.out.println("svar - " + svar);
                Gson gson = new Gson();
                Type listType = (new TypeToken<List<OnBetaling>>() {

                }).getType();
                final List<Betaling> betalinger = gson.fromJson(svar, listType);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    public void run() {
                        for (Betaling betal : betalinger)
                            Bukkit.getServer().getPluginManager().callEvent(new OnBetaling(

                                    Bukkit.getOfflinePlayer(
                                            UUID.fromString(betal.getUuid())), betal

                                    .getPakke(), betal
                                    .getAmount(), betal
                                    ));
                    }
                }, 0L);
            }
        });
    }
}
