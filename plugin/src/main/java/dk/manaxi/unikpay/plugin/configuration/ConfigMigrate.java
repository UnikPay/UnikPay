package dk.manaxi.unikpay.plugin.configuration;

import dk.manaxi.unikpay.plugin.Main;
import org.spongepowered.configurate.*;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.spongepowered.configurate.yaml.internal.snakeyaml.LoaderOptions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigMigrate {
    public static boolean migrate() throws ConfigurateException {
        final Path config = Main.getInstance().getDataFolder().toPath().resolve("config.yml");
        final Path lang = Main.getInstance().getDataFolder().toPath().resolve("lang.yml");
        if (!Files.exists(config)) {
            return false;
        }
        if(!Files.exists(lang)) {
            try {
                Files.createFile(lang);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder().path(config).build();
        ConfigurationNode oldNode = loader.load();
        if(!oldNode.node("version").getString("").isEmpty()) {
            return false;
        }
        ConfigurationOptions configurationOptions = ConfigurationOptions.defaults();

        YamlConfigurationLoader langLoader = YamlConfigurationLoader.builder()
                .defaultOptions(configurationOptions)
                .path(lang)
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder()
                .defaultOptions(configurationOptions)
                .path(config)
                .nodeStyle(NodeStyle.BLOCK)
                .build();
        CommentedConfigurationNode newLang = langLoader
                .load();
        CommentedConfigurationNode newConfig = configLoader
                .load();

        try (InputStream stream = Main.getInstance().getResource("config.yml")) {
            assert stream != null;
            newConfig.raw(null);
            newConfig.node("version").set("1.0.0");
            newConfig.node("api-key").set(oldNode.node("Api-key").getString("KEY HER"));
            newConfig.node("admin-permission").set(oldNode.node("admin-permission").getString("unikpay.admin"));
            newConfig.node("skript-hook").set(oldNode.node("skript-hook").getBoolean(true));
            newConfig.node("update-notify").set(oldNode.node("update-notify").getBoolean(true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (InputStream stream = Main.getInstance().getResource("lang.yml")) {
            assert stream != null;
            newLang.node("version").set("1.0.0");
            String noPermission = oldNode.node("no-permission").getString("{prefix} &cDu har ikke adgang til denne kommando.");
            newLang.node("nopermission").setList(String.class, Collections.singletonList(convertLegacyToMM(noPermission)));
            String prefix = oldNode.node("prefix").getString("&8&l[ &a&lUNIKPAY &8&l]");
            newLang.node("prefix").setList(String.class, Collections.singletonList(convertLegacyToMM(prefix)));
            List<String> ikkelinket = oldNode.node("ikkelinket").getList(String.class, Arrays.asList("{prefix} &cDet ser ud til, at du ikke har en bruger endnu!", "{prefix} &cJoin vores Discord-server: ( https://discord.gg/jnWKcuVmWf ) for at oprette en bruger.", "{prefix} &cOg skriv &a/link &ci en hvilken som helst kanal du har adgang til at skrive i."));
            newLang.node("ikkelinket").setList(String.class, convertLegacyToMM(ikkelinket));
            List<String> accepterbetaling = oldNode.node("accepterbetaling").getList(String.class, Collections.singletonList("{prefix} &aAccepter købet på Discord."));
            newLang.node("accepterbetaling").setList(String.class, convertLegacyToMM(accepterbetaling));
            List<String> paysuccess = oldNode.node("paysuccess").getList(String.class, Collections.singletonList("{prefix} &aDu er blevet payet!"));
            newLang.node("paysuccess").setList(String.class, convertLegacyToMM(paysuccess));
            List<String> ratetime = oldNode.node("ratetime").getList(String.class, Collections.singletonList("{prefix} &cDu skal lige vente 15 sekunder, før du kan købe igen."));
            newLang.node("ratetime").setList(String.class, convertLegacyToMM(ratetime));
            List<String> allerede_sub = oldNode.node("allerede_sub").getList(String.class, Collections.singletonList("{prefix} &cDu har allerede abbonneret til det."));
            newLang.node("allerede_sub").setList(String.class, convertLegacyToMM(allerede_sub));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        langLoader.save(newLang);
        configLoader.save(newConfig);
        return true;
    }

    public static List<String> convertLegacyToMM(List<String> strings) {
        List<String> newStrings = new ArrayList<>();
        for (String string : strings) {
            newStrings.add(convertLegacyToMM(string));
        }

        return newStrings;
    }

    public static String convertLegacyToMM(String string) {
        return string
                .replace("&0", "<black>")
                .replace("&1", "<dark_blue>")
                .replace("&2", "<dark_green>")
                .replace("&3", "<dark_aqua>")
                .replace("&4", "<dark_red>")
                .replace("&5", "<dark_purple>")
                .replace("&6", "<gold>")
                .replace("&7", "<gray>")
                .replace("&8", "<dark_gray>")
                .replace("&9", "<blue>")
                .replace("&a", "<green>")
                .replace("&b", "<aqua>")
                .replace("&c", "<red>")
                .replace("&d", "<light_purple>")
                .replace("&e", "<yellow>")
                .replace("&f", "<white>")
                .replace("&k", "<obfuscated>")
                .replace("&l", "<bold>")
                .replace("&m", "<strikethrough>")
                .replace("&n", "<underlined>")
                .replace("&o", "<italic>")
                .replace("&r", "<reset>")
                .replace("{prefix}", "<prefix>");
    }
}
