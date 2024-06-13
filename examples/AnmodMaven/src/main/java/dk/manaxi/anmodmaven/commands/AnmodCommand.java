package dk.manaxi.anmodmaven.commands;

import dk.manaxi.unikpay.plugin.API.Internal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnmodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        player.sendMessage("§aAnmoder om fly!");
        Internal.sendPackageRequest(player, "fly", 1000, null);

        return false;
    }
}
