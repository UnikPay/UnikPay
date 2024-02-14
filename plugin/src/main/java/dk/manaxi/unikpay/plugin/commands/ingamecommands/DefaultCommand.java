package dk.manaxi.unikpay.plugin.commands.ingamecommands;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
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
        Main.getInstance().getLang().send(player, "unikpay.help.default", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
        if ((sender.hasPermission(Main.configYML.getString("admin-permission")) && !sender.hasPermission("unik.command")) || sender.isOp()) {
            Main.getInstance().getLang().send(player, "unikpay.help.admin");
        }
    }
}
