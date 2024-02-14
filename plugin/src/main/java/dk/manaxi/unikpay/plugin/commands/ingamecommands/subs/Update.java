package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import dk.manaxi.unikpay.plugin.manager.UpdateManager;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Update extends ISubCommand {
    public Update() {
        super("update");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;
        if (!player.hasPermission(Main.configYML.getString("admin-permission")) && !player.hasPermission("unikpay.reload")) {
            Main.getInstance().getLang().send(player, "no-permission");
            return;
        }
        Main.getInstance().getLang().send(player, "unikpay.update.start",
                Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
        try {
            UpdateManager.Update();
            Main.getInstance().getLang().send(player, "unikpay.update.success",
                    Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
        } catch (IOException e) {
            Main.getInstance().getLang().send(player, "unikpay.update.error",
                    Placeholder.component("prefix", Main.getInstance().getLang().get("prefix")));
            e.printStackTrace();
        }
    }
}
