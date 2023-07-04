package dk.manaxi.unikpay.plugin.commands.ingamecommands;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DefaultCommand extends ISubCommand {

    public DefaultCommand() {
        super("default");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;
        Config.send(sender, "prefix");
        player.sendMessage(ColorUtils.getColored("  &7/unikpay discord"));
        if (sender.hasPermission(Main.configYML.getString("admin-permission")) && !sender.hasPermission("unik.command")) {
            player.sendMessage(ColorUtils.getColored("  &7/unikpay reload"));

        }
    }
}
