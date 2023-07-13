package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.enums.Hook;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import dk.manaxi.unikpay.plugin.websocket.IoSocket;
import io.socket.client.Socket;
import org.bukkit.command.CommandSender;

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
        sender.sendMessage(ColorUtils.getColored(" &fWebSocket connected: " + (!IoSocket.getSocket().connected() ? "&cFalse" : "&aTrue")));
        sender.sendMessage(ColorUtils.getColored(" &fApikey indsat: " + (Main.getAPIKEY() == null ? "&cFalse" : "&aTrue")));
        sender.sendMessage(ColorUtils.getColored(" &fSkript connected: " + (!Main.isHookInitialised(Hook.SKRIPT) ? "&cFalse" : "&aTrue")));
        if (UpdateManager.isNewestVersionAvailable(Main.getInstance().getDescription().getVersion())) return;
        sender.sendMessage(ColorUtils.getColored(" &fDer er en ny version af &aUnikpay.Jar"));
        sender.sendMessage(ColorUtils.getColored(" &fBenyt dig af &a/unikpay update"));
    }
}
