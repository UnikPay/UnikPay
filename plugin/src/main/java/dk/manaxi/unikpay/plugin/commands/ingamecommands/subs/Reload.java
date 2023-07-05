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
        sender.sendMessage(Config.get("prefix")[0] + ColorUtils.getColored(" &fReloader pluginet"));
        try {
            Main.getInstance().reload();
            sender.sendMessage(Config.get("prefix")[0] + ColorUtils.getColored("&aDu genindlaeste alt. &7(" + (System.currentTimeMillis() - timestampBeforeLoad) + " ms)"));
        } catch (Exception e) {
            sender.sendMessage(Config.get("prefix")[0] + ColorUtils.getColored("&cDer skete en fejl under reload, tjek loggen eller din config.yml"));
            e.printStackTrace();
        }

    }

}
