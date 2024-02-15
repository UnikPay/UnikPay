package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends ISubCommand {
    public Reload() {
        super("reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;
        //Checks, if player have permission.
        if (!player.hasPermission(Main.getInstance().getConfigSystem().getADMINPERMISSION()) && !player.hasPermission("unikpay.reload")) {
                Main.getInstance().getLang().send(player, "no-permission");
            return;
        }

        long timestampBeforeLoad = System.currentTimeMillis();
        Main.getInstance().getInternalLang().send(player, "unikpay.reload.reloading", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
        try {
            Main.getInstance().reload();
            Main.getInstance().getInternalLang().send(player, "unikpay.reload.reloaded", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")), Placeholder.unparsed("time", String.valueOf(System.currentTimeMillis() - timestampBeforeLoad)));
        } catch (Exception e) {
            Main.getInstance().getInternalLang().send(player, "unikpay.reload.error", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
            Main.getInstance().getLogger().severe("An error occurred while reloading the plugin: " + e.getMessage());
        }

    }

}
