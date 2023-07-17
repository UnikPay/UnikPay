package dk.manaxi.unikpay.plugin.API;

import dk.manaxi.unikpay.api.classes.Pakke;
import dk.manaxi.unikpay.plugin.manager.RequestManager;
import dk.manaxi.unikpay.plugin.skript.classes.AcceptId;
import org.bukkit.entity.Player;

public class Internal {
    public static void sendPackageRequest(Player player, Pakke[] packages) {
        RequestManager.sendPackageRequest(player, packages);
    };
    public static void sendPayRequest(Player player, String name, Number amount) {
        RequestManager.sendPayRequest(player, name, amount);
    };
    public static void sendPackageRequest(Player player, String pakke, Number price, String id) {
        RequestManager.sendPackageRequest(player, pakke, price, id);
    };
    public static void acceptPackageRequest(String id) {
        RequestManager.acceptPackageReqeust(id);
    };

    public static void acceptPackageRequest(AcceptId Id) {
        RequestManager.acceptPackageReqeust(Id.toString());
    };
}
