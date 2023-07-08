package dk.manaxi.unikpay.plugin.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Config {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String folderName, fileName;

    public Config(JavaPlugin instance, String folderName, String fileName) {
        this.plugin = instance;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public FileConfiguration getConfig() {
        if (this.config == null) reloadConfig();
        return this.config;
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            if (this.folderName != null && !this.folderName.isEmpty()) {
                this.configFile = new File(this.plugin.getDataFolder() + File.separator + this.folderName, this.fileName);
            } else {
                this.configFile = new File(this.plugin.getDataFolder(), this.fileName);
            }
        }
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void saveConfig() {
        if (this.config == null || this.configFile == null) return;
        try {
            getConfig().save(this.configFile);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to" + this.configFile, ex);
        }
    }
}