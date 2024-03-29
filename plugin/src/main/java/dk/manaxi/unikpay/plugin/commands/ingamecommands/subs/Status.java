package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.enums.Hook;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.websocket.IoSocket;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Status extends ISubCommand {
    public Status() {
        super("status");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;
        if (!player.hasPermission(Main.getInstance().getConfigSystem().getADMINPERMISSION()) && !player.hasPermission("unikpay.reload")) {
            Main.getInstance().getLang().send(player, "no-permission");
            return;
        }
        Main.getInstance().getInternalLang().send(player, "unikpay.status.info",
                Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")),
                Placeholder.unparsed("version", Main.getInstance().getDescription().getVersion()),
                Placeholder.parsed("wsstatus", (!IoSocket.getSocket().connected() ? "<#55FF55>✖" : "<#55FF55>✓")),
                Placeholder.parsed("apistatus", (!isURLAvailable(Main.getInstance().getConfigSystem().getUrl()) ? "<#FF5555>✖" : "<#55FF55>✓")),
                Placeholder.parsed("apikey", (Main.getAPIKEY() == null ? "<#FF5555>✖" : "<#55FF55>✓")),
                Placeholder.parsed("skriptstatus", (!Main.isHookInitialised(Hook.SKRIPT) ? "<#FF5555>✖" : "<#55FF55>✓"))
        );
        if (Main.getInstance().getDescription().getVersion().split("-").length > 1 && Main.getInstance().getDescription().getVersion().split("-")[1].equals("SNAPSHOT")) {
            Main.getInstance().getInternalLang().send(player, "unikpay.status.snapshot",
                    Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
        }
        if (UpdateManager.isANewVersionAvailable(Main.getInstance().getDescription().getVersion())) {
            Main.getInstance().getInternalLang().send(player, "unikpay.status.update",
                    Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
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
