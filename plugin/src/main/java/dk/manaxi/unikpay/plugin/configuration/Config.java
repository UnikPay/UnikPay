package dk.manaxi.unikpay.plugin.configuration;

import dk.manaxi.unikpay.plugin.Main;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private ConfigurationNode node;
    private String splitNode(String key, String defaultValue) {
        String[] nodes = key.split("\\.");
        ConfigurationNode node = this.node;
        for (String subNode : nodes) {
            node = node.node(subNode);
        }
        return node.getString(defaultValue);
    }

    private Boolean splitNode(String key, Boolean defaultValue) {
        String[] nodes = key.split("\\.");
        ConfigurationNode node = this.node;
        for (String subNode : nodes) {
            node = node.node(subNode);
        }
        return node.getBoolean(defaultValue);
    }

    public String getUrl() {
        return splitNode("url", "https://unikpay.manaxi.dk");
    }
    public String getAPIKEY() {
        return splitNode("api-key", "KEY HER");
    }

    public String getADMINPERMISSION() {
        return splitNode("admin-permission", "unikpay.admin");
    }

    public Boolean getSKRIPTHOOK() {
        return splitNode("skript-hook", true);
    }

    public Boolean getUPDATENOTIFY() {
        return splitNode("update-notify", true);
    }

    public void load() throws IOException {
        final Path config = Main.getInstance().getDataFolder().toPath().resolve("config.yml");
        if (!Files.exists(config)) {
            try (InputStream stream = Main.getInstance().getResource("config.yml")) {
                assert stream != null;
                Files.copy(stream, config);
            }
        }
        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder().path(config).build();
        this.node = loader.load();
        // Convert the InputStream to a ConfigurationNode
    }
}
