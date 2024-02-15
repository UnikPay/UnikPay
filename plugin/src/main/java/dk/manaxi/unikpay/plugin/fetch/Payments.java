package dk.manaxi.unikpay.plugin.fetch;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.api.classes.Betaling;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.event.OnBetaling;
import dk.manaxi.unikpay.plugin.event.OnSubscriptionPayment;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.*;

@SuppressWarnings("unchecked")
public class Payments {
    private static long lastPaymentFetch = 0L;
    private static final String url = Main.getInstance().getConfigSystem() + "/v1/request";

    private static final Set<String> calledIds = Collections.synchronizedSet(new HashSet<>());
    private static final Set<String> lastSeenIds = Collections.synchronizedSet(new HashSet<>());

    public static void fetchPayments() {
        if (lastPaymentFetch > (new Date()).getTime() - 5000L)
            return;
        if(Main.getAPIKEY() == null) return;
        lastPaymentFetch = (new Date()).getTime();
        Gson gson = new Gson();
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            String svar = HttpsClient.getRequest(url, Main.getAPIKEY());
            JsonObject svarOBJ = gson.fromJson(svar, JsonObject.class);
            Type listType = (new TypeToken<List<Betaling>>() {}).getType();
            final List<Betaling> betalinger = gson.fromJson(svarOBJ.getAsJsonArray("requests"), listType);

            lastSeenIds.clear();

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                public void run() {
                    for (Betaling betal : betalinger) {
                        if(calledIds.contains(betal.get_id())) {
                            continue;
                        }
                        lastSeenIds.add(betal.get_id());
                        if (betal.getSubscription() == null) {
                            Bukkit.getServer().getPluginManager().callEvent(new OnBetaling(
                                    Bukkit.getOfflinePlayer(betal.getMcaccount().getUuid()),
                                    betal.getPackages(),
                                    betal.getAmount(),
                                    new AcceptId(betal.get_id())
                            ));
                        } else {
                            Bukkit.getServer().getPluginManager().callEvent(new OnSubscriptionPayment(
                                    Bukkit.getOfflinePlayer(betal.getMcaccount().getUuid()),
                                    betal.getPackages(),
                                    betal.getAmount(),
                                    betal.getSubscription(),
                                    new AcceptId(betal.get_id())
                            ));
                        }
                    }
                    calledIds.clear();
                }
            }, 0L);
        });
    }

    public static boolean isSeen(String id) {
        return lastSeenIds.contains(id);
    }

    public static void addHandledBySocket(String id) {
        calledIds.add(id);
    }
}
