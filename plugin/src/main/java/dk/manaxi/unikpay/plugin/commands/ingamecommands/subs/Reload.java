package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.command.CommandSender;

public class Reload extends ISubCommand {
    public Reload() {
        super("reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        //Checks, if player have permission.
        if (!sender.hasPermission(Main.configYML.getString("admin-permission")) && !sender.hasPermission("unikpay.reload")) {
                Config.send(sender, "no-permission");
            return;
        }
        long timestampBeforeLoad = System.currentTimeMillis();
        sender.sendMessage(ColorUtils.getColored("&aStarted reloading yml"));
        try {
            Main.getInstance().reload();
            sender.sendMessage(ColorUtils.getColored("&aSuccessful reloaded all &7took (" + (System.currentTimeMillis() - timestampBeforeLoad) + " ms)"));
        } catch (Exception e) {
            sender.sendMessage(ColorUtils.getColored("&cDer skete en fejl under reload"));
            e.printStackTrace();
        }

    }

}
