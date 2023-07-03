package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.command.CommandSender;

public class Discord extends ISubCommand {
    public Discord() {
        super("discord");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        sender.sendMessage(Config.get("prefix")[0] + ColorUtils.getColored(" &fJoin vores discord &ahttps://discord.gg/jnWKcuVmWf"));
    }
}
