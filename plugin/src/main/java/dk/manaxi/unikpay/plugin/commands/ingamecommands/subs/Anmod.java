package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import com.google.gson.Gson;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpsClient;
import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.commands.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Anmod extends ISubCommand {
    public Anmod() {
        super("anmod");
    }
    private static String url = Config.MAINURL + "request";

    @Override
    public void onCommand(CommandSender sender, String[] args, String paramString) {
        Player player = (Player) sender;


        // Print the payload, API key, and URL for debugging
       // System.out.println("Payload: " + jsonReq);
        System.out.println("API Key: " + Main.getAPIKEY());
        System.out.println("URL: " + url);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            public void run() {
                String svar = HttpsClient.post(url, jsonReq, Main.getAPIKEY());
                System.out.println("Svar -" + svar);
            }
        });

    }




}
