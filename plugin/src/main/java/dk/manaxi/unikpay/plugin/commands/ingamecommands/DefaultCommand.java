package dk.manaxi.unikpay.plugin.commands.ingamecommands;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
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
        Main.getInstance().getInternalLang().send(player, "unikpay.help.default", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
        if ((sender.hasPermission(Main.getInstance().getConfigSystem().getADMINPERMISSION()) && !sender.hasPermission("unik.command")) || sender.isOp()) {
            Main.getInstance().getInternalLang().send(player, "unikpay.help.admin");
        }
    }
}
