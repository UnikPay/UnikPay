package dk.manaxi.unikpay.plugin.fetch;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.api.classes.Betaling;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import org.bukkit.Bukkit;
import dk.manaxi.unikpay.plugin.skript.classes.id;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class Payments {
    private static long lastPaymentFetch = 0L;
    private static String url = Config.MAINURL + "request";

    public static void fetchPayments() {
        if (lastPaymentFetch > (new Date()).getTime() - 5000L)
            return;
        lastPaymentFetch = (new Date()).getTime();
        Gson gson = new Gson();
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.getRequest(url, Main.getAPIKEY());
            JsonObject svarOBJ = gson.fromJson(svar, JsonObject.class);
            Type listType = (new TypeToken<List<Betaling>>() {}).getType();
            final List<Betaling> betalinger = (List<Betaling>) gson.fromJson(svarOBJ.getAsJsonArray("requests"), listType);

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    for (Betaling betal : betalinger)
                        Bukkit.getServer().getPluginManager().callEvent(new OnBetaling(
                                Bukkit.getOfflinePlayer(betal.getMcaccount().getUuid()),
                                betal.getPackages(),
                                betal.getAmount(),
                                new id(betal.get_id())
                        ));
                }
            }, 0L);
        });
    }
}
