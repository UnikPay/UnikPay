package dk.manaxi.unikpay.plugin.commands.ingamecommands;

import dk.manaxi.unikpay.plugin.commands.ISubCommand;
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

    }
}
