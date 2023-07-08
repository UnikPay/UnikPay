package dk.manaxi.unikpay.plugin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class ICommand implements CommandExecutor, TabCompleter {
    private final Collection<ISubCommand> subCommands = new ArrayList<>();

    private ISubCommand defaultCommand = null;

    public ICommand(JavaPlugin plugin, String command) {
        if (plugin.getCommand(command) != null) {
            plugin.getCommand(command).setExecutor(this);
            plugin.getCommand(command).setTabCompleter(this);
        } else {
            plugin.getCommand(command).setTabCompleter(this);
        }
    }
    protected ISubCommand getSubCommand(String name) {
        for (ISubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(name)) {
                return subCommand;
            }
        }
        return null;
    }


    public List<ISubCommand> getSubCommands() {
        return new ArrayList<>(subCommands);
    }

    protected void setDefaultCommand(ISubCommand subCommand) {
        this.defaultCommand = subCommand;
    }

    protected ISubCommand getDefaultCommand() {
        return this.defaultCommand;
    }

    protected void addSubCommand(ISubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    protected void addSubCommands(ISubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    protected ISubCommand findSubCommand(String command) {
        for (ISubCommand subCommand : getSubCommands()) {
            if (subCommand.getName().equalsIgnoreCase(command))
                return subCommand;
            for (String alias : subCommand.getAliases()) {
                if (alias.equalsIgnoreCase(command))
                    return subCommand;
            }
        }
        return null;
    }

    protected void execute(CommandSender sender, ISubCommand subCommand, String[] arguments) {
        String[] newArguments = null;
        if (arguments.length > 0) {
            newArguments = new String[arguments.length - 1];
            System.arraycopy(arguments, 1, newArguments, 0, arguments.length - 1);
        }
        subCommand.onCommand(sender,
                (newArguments != null) ? newArguments : new String[0],
                (arguments.length > 0) ? arguments[0] : "");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase(getCommandName())) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                for (ISubCommand subCommand : getSubCommands()) {
                    completions.add(subCommand.getName());
                }
                return completions;
            }
        }
        return null;
    }

    protected abstract String getCommandName();
}