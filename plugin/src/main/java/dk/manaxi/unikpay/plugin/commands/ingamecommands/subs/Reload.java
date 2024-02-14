package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
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
        if (!player.hasPermission(Main.configYML.getString("admin-permission")) && !player.hasPermission("unikpay.reload")) {
                Main.getInstance().getLang().send(player, "no-permission");
            return;
        }

        long timestampBeforeLoad = System.currentTimeMillis();
        Main.getInstance().getLang().send(player, "unikpay.reload.reloading");
        try {
            Main.getInstance().reload();
            Main.getInstance().getLang().send(player, "unikpay.reload.reloaded", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")), Placeholder.unparsed("time", String.valueOf(System.currentTimeMillis() - timestampBeforeLoad)));
        } catch (Exception e) {
            Main.getInstance().getLang().send(player, "unikpay.reload.error", Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
            e.printStackTrace();
        }

    }

}
