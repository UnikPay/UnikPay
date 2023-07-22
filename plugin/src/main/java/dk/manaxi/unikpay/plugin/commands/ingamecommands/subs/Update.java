package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class Update extends ISubCommand {
    public Update() {
        super("update");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if (!sender.hasPermission(Main.configYML.getString("admin-permission")) && !sender.hasPermission("unikpay.reload")) {
            Config.send(sender, "no-permission");
            return;
        }
        sender.sendMessage(Config.get("prefix")[0]);
        sender.sendMessage(ColorUtils.getColored(" &fBegynder at opdatere pluginet"));
        try {
            UpdateManager.Update();
            sender.sendMessage(ColorUtils.getColored(" &aDu downloaded og installerede den nyeste version af pluginet."));
        } catch (IOException e) {
            sender.sendMessage(ColorUtils.getColored(" &cDer skete en fejl, under opdatering. &7&o( &fTjek logs &7&o)"));
            throw new RuntimeException(e);

        }
    }
}
