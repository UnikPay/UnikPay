package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.enums.Hook;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import dk.manaxi.unikpay.plugin.websocket.IoSocket;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Status extends ISubCommand {
    public Status() {
        super("status");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if (!sender.hasPermission(Main.configYML.getString("admin-permission")) && !sender.hasPermission("unikpay.reload")) {
            Config.send(sender, "no-permission");
            return;
        }
        sender.sendMessage(Config.get("prefix")[0]);
        sender.sendMessage(ColorUtils.getColored(" &fVersion: &a" + Main.getInstance().getDescription().getVersion()));
        sender.sendMessage(ColorUtils.getColored(" &fWebSocket connected: " + (!IoSocket.getSocket().connected() ? "&c✖" : "&a✓")));
        sender.sendMessage(ColorUtils.getColored(" &fApi connected: " + (!isURLAvailable(dk.manaxi.unikpay.api.Config.MAINURL) ? "&c✖" : "&a✓")));
        sender.sendMessage(ColorUtils.getColored(" &fApikey indsat: " + (Main.getAPIKEY() == null ? "&c✖" : "&a✓")));
        sender.sendMessage(ColorUtils.getColored(" &fSkript connected: " + (!Main.isHookInitialised(Hook.SKRIPT) ? "&c✖" : "&a✓")));
        if (Main.getInstance().getDescription().getVersion().split("-")[1].equals("SNAPSHOT")) {
            sender.sendMessage(ColorUtils.getColored("&7 "));
            sender.sendMessage(ColorUtils.getColored(" &fDu kører en snapshot version af UnikPay og det er ikke anbefalet!"));
            sender.sendMessage(ColorUtils.getColored(" &fBenyt dig af &a/unikpay update"));
        }
        if (UpdateManager.isANewVersionAvailable(Main.getInstance().getDescription().getVersion())) {
            sender.sendMessage(ColorUtils.getColored("&7 "));
            sender.sendMessage(ColorUtils.getColored(" &fDer er en ny version af &aUnikpay.Jar"));
            sender.sendMessage(ColorUtils.getColored(" &fBenyt dig af &a/unikpay update"));
        }
    }


    private static boolean isURLAvailable(String urlString) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlString)
                .head()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
    }

}
