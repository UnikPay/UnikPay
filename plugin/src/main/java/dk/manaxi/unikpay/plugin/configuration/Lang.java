package dk.manaxi.unikpay.plugin.configuration;

import dk.manaxi.unikpay.plugin.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Lang {
    private ConfigurationNode node;

    public void send(Player player, String key, TagResolver... args) {
        Component message = get(key, args);
        if(message == null) {
            return;
        }
        if (message.equals(Component.empty())) {
            return;
        }
        Main.getInstance().getAdventure().player(player).sendMessage(message);
    }

    public List<Component> getList(String key, TagResolver... args) {
        String[] nodes = key.split("\\.");
        ConfigurationNode node = this.node;
        for (String subNode : nodes) {
            node = node.node(subNode);
        }
        List<Component> translations = new ArrayList<>();
        try {
            List<String> strings = node.getList(String.class);
            assert strings != null;
            for (String s : strings) {
                translations.add(MiniMessage.miniMessage().deserialize(s, args));
            }
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
        if (translations.isEmpty()) {
            translations.add(MiniMessage.miniMessage().deserialize("<red>Missing translation for <yellow>" + key));
        }
        return translations;
    }

    public Component get(String key) {
        if(!getList(key).isEmpty()) {
            return Component.join(JoinConfiguration.newlines(), getList(key));
        }
        String translation = splitNode(key);
        return MiniMessage.miniMessage().deserialize(translation);
    }

    private String splitNode(String key) {
        String[] nodes = key.split("\\.");
        ConfigurationNode node = this.node;
        for (String subNode : nodes) {
            node = node.node(subNode);
        }
        String translation = node.getString();
        if (translation == null) {
            translation = "<red>Missing translation for <yellow>" + key;
        }
        return translation;
    }

    public Component get(String key, TagResolver... args) {
        if(!getList(key, args).isEmpty()) {
            return Component.join(JoinConfiguration.newlines(), getList(key, args));
        }
        String translation = splitNode(key);
        return MiniMessage.miniMessage().deserialize(translation, args);
    }

    public void load() throws IOException {
        final Path lang = Main.getInstance().getDataFolder().toPath().resolve("lang.yml");
        if (!Files.exists(lang)) {
            try (InputStream stream = Main.getInstance().getResource("lang.yml")) {
                assert stream != null;
                Files.copy(stream, lang);
            }
        }
        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder().path(lang).build();
        this.node = loader.load();
        // Convert the InputStream to a ConfigurationNode
    }
}
