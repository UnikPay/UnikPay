package dk.manaxi.unikpay.plugin.commands.ingamecommands;

import dk.manaxi.unikpay.plugin.commands.ICommand;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.commands.ingamecommands.subs.Discord;
import dk.manaxi.unikpay.plugin.commands.ingamecommands.subs.Reload;
import dk.manaxi.unikpay.plugin.commands.ingamecommands.subs.Status;
import dk.manaxi.unikpay.plugin.commands.ingamecommands.subs.Update;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnikPayCommand extends ICommand {
    public UnikPayCommand(JavaPlugin plugin, String command) {
        super(plugin, command);

        setDefaultCommand(new DefaultCommand());
        addSubCommands(
                new Reload(),
                new Discord(),
                new Status(),
                new Update()
        );

    }

    @Override
    protected String getCommandName() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && getDefaultCommand() != null) {
            execute(sender, getDefaultCommand(), args);
        } else if (args.length > 0) {
            ISubCommand subCommand = findSubCommand(args[0]);
            if (subCommand != null) {
                execute(sender, subCommand, args);
            }
            return true;
        }
        return false;
    }
}
