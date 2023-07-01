package dk.manaxi.unikpay.plugin.commands;

import dk.manaxi.unikpay.plugin.commands.ingamecommands.UnikPayCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteListener implements TabCompleter {
    private ICommand commandManager;

    public TabCompleteListener(UnikPayCommand unikPayCommand) {
        this.commandManager = unikPayCommand;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> completions = new ArrayList<>();
            for (ISubCommand subCommand : commandManager.getSubCommands()) {
                String subCommandName = subCommand.getName().toLowerCase();
                if (subCommandName.startsWith(input)) {
                    completions.add(subCommand.getName());
                }
            }
            return completions;
        }
        return null;
    }
}
