package dk.manaxi.unikpay.plugin.configuration;

import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class Config {
    private static Map<String, String[]> configs;

    public static void loadALl() {
        configs = (HashMap)new HashMap<>();
        for (String path : Main.configYML.getKeys(true)) {
            if (!Main.configYML.isConfigurationSection(path)) {
                if (Main.configYML.getStringList(path) != null && Main.configYML.isList(path)) {
                    List<String> stringList = ColorUtils.getColored(Main.configYML.getStringList(path));
                    configs.put(path, stringList.<String>toArray(new String[0]));
                    continue;
                }
                if (Main.configYML.getString(path) != null) {
                    List<String> stringList = Collections.singletonList(ColorUtils.getColored(Main.configYML.getString(path)));
                    configs.put(path, stringList.<String>toArray(new String[0]));
                }
            }
        }
        if (configs.containsKey("prefix")) {
            String prefix = configs.get("prefix")[0];
            for (String[] values : configs.values()) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replace("{prefix}", prefix);
                }
            }
        }
    }
    public static String[] get(String path) {
        return configs.getOrDefault(path, new String[]{});
    }

    public static String[] get(String path, String... replacements) {
        if (configs.containsKey(path)) {
            String[] messages = get(path);
            List<String> messageList = new ArrayList<>();
            for (String message : messages) {
                for (int i = 0; i < replacements.length; i += 2) {
                    message = message.replaceAll(replacements[i], replacements[i + 1]);
                }
                messageList.add(message);
            }
            return messageList.toArray(new String[0]);
        }
        return new String[]{};
    }

    private static void sendMessages(CommandSender player, String path, String... replacements) {
        String[] messages = get(path);
        for (String message : messages) {
            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replaceAll(replacements[i], replacements[i + 1]);
            }
            player.sendMessage(message);
        }
    }

    public static void send(CommandSender player, String path, String... replacements) {
        sendMessages(player, path, replacements);
    }

    public static void send(Player player, String path, String... replacements) {
        sendMessages(player, path, replacements);
    }

    public static void broadcast(String path) {
        String[] messages = get(path);
        for (String message : messages) {
            Bukkit.broadcastMessage(message);
        }
    }

    public static void broadcast(String path, Object... replacements) {
        String[] messages = get(path);
        for (String message : get(path)) {
            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replaceAll((String) replacements[i], (String) replacements[i+1]);
            }
            Bukkit.broadcastMessage(message);
        }
    }

    public static void sendToPerms(String path, String permission) {
        String[] messages = get(path);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                for (String message : messages) {
                    player.sendMessage(message);
                }
            }
        }
    }

    public static void sendToPerms(String path, String permission, Object... replacements) {
        String[] messages = get(path);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                for (String message : messages) {
                    for (int i = 0; i < replacements.length; i += 2) {
                        message = message.replaceAll((String) replacements[i], (String) replacements[i+1]);
                    }
                    player.sendMessage(message);
                }
            }
        }
    }


}
