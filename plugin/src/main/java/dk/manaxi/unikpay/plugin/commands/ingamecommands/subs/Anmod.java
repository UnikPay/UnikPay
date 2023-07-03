package dk.manaxi.unikpay.plugin.commands.ingamecommands.subs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dk.manaxi.unikpay.api.Config;
import dk.manaxi.unikpay.api.HttpClient;
import dk.manaxi.unikpay.api.classes.CustomPackage;
import dk.manaxi.unikpay.api.classes.Request;
import dk.manaxi.unikpay.api.classes.RequestBody;
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
        Request req = new Request();
        req.setBody(new RequestBody());
        req.getBody().setUuid("f1fea669-4ec9-40ed-87a4-a4ff71859ecd");
        req.getBody().setPakker(new CustomPackage[] {
                new CustomPackage("Package 1", "123", 10),
                new CustomPackage("Package 2", "456", 20)
        });
        Gson gson = new Gson();
        String jsonReq = gson.toJson(req);

        // Print the payload, API key, and URL for debugging
        System.out.println("Payload: " + jsonReq);
        System.out.println("API Key: " + Main.getAPIKEY());
        System.out.println("URL: " + url);

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
            public void run() {
                String svar = HttpClient.post(url, jsonReq, Main.getAPIKEY());
                System.out.println("Svar -" + svar);
            }
        });

    }




}
