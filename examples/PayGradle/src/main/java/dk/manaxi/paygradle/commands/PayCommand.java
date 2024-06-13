package dk.manaxi.paygradle.commands;

import dk.manaxi.unikpay.plugin.API.Internal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length != 2) {
            player.sendMessage("§cBrug /pay <spiller> <beløb>");
            return false;
        }

        if (!args[1].matches("[0-9]+")) {
            player.sendMessage("§cBeløb skal være et tal!");
            return false;
        }

        player.sendMessage("§aPayet " + args[0] + " " + args[1] + "kr.!");
        // remove args[1] from player
        Player player1 = Bukkit.getPlayer(args[0]);
        if (player1 == null) {
            player.sendMessage("§cSpilleren er ikke online!");
            return false;
        }
        Internal.sendPayRequest(player1, "Udbetaling af " + args[1], Integer.parseInt(args[1]));
        return false;
    }
}
