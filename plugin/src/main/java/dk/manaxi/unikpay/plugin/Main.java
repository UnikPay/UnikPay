package dk.manaxi.unikpay.plugin;

import dk.manaxi.unikpay.plugin.commands.CommandManager;
import dk.manaxi.unikpay.plugin.configuration.Config;
import dk.manaxi.unikpay.plugin.configuration.ConfigMigrate;
import dk.manaxi.unikpay.plugin.configuration.InternalLang;
import dk.manaxi.unikpay.plugin.configuration.Lang;
import dk.manaxi.unikpay.plugin.enums.Hook;
import dk.manaxi.unikpay.plugin.fetch.Payments;
import dk.manaxi.unikpay.plugin.hooks.SkriptHook;
import dk.manaxi.unikpay.plugin.interfaces.IHook;
import dk.manaxi.unikpay.plugin.listeners.OnSync;
import dk.manaxi.unikpay.plugin.websocket.Console;
import dk.manaxi.unikpay.plugin.websocket.IoSocket;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spongepowered.configurate.ConfigurateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public final class Main extends JavaPlugin {
    @Getter
    private BukkitAudiences adventure;
    @Getter
    private static Main instance;
    @Getter
    private Lang lang;
    @Getter
    private InternalLang internalLang;
    @Getter
    public Config configSystem;
    public static ConsoleCommandSender log;
    private static final HashMap<Hook, Boolean> HOOKS = new HashMap<>();
    @Getter
    private static String APIKEY;

    @Override
    public void onEnable() {
        long timestampBeforeLoad = System.currentTimeMillis();
        instance = this;
        this.adventure = BukkitAudiences.create(this);
        try {
            this.internalLang = new InternalLang();
            this.internalLang.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ConfigMigrate.migrate();
        } catch (ConfigurateException e) {
            getLogger().severe("An error occurred while migrating the config: " + e.getMessage());
        }
        log = Bukkit.getConsoleSender();
        Main.getInstance().getInternalLang().send(adventure.console(), "console.enabling", Placeholder.unparsed("version", getDescription().getVersion()));
        Main.getInstance().getInternalLang().send(adventure.console(), "console.config");
        initialiseConfigs();

        Main.getInstance().getInternalLang().send(adventure.console(), "console.commands");
        CommandManager.initialise(this);

        Main.getInstance().getInternalLang().send(adventure.console(), "console.integrations");
        initialiseHooks();

        Main.getInstance().getInternalLang().send(adventure.console(), "console.webSocket");
        IoSocket.connectSocket();
        if(configSystem.getUPDATENOTIFY()) {
            Bukkit.getServer().getPluginManager().registerEvents(new OnSync(), this);
        }

        Main.getInstance().getInternalLang().send(adventure.console(), "console.console");
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addAppender(new Console());

        Main.getInstance().getInternalLang().send(adventure.console(), "console.done",
                Placeholder.unparsed("time", String.valueOf(System.currentTimeMillis() - timestampBeforeLoad)),
                Placeholder.unparsed("version", getDescription().getVersion()),
                Placeholder.unparsed("authors", String.join(", ", getDescription().getAuthors()))
        );

        (new BukkitRunnable() {
            @Override
            public void run() {
                Payments.fetchPayments();
            }
        }).runTaskTimerAsynchronously(Main.getInstance(), 20L, 600L);
    }

    @Override
    public void onDisable() {
        Main.getInstance().getInternalLang().send(adventure.console(), "console.disabled",
                Placeholder.unparsed("version", getDescription().getVersion()),
                Placeholder.unparsed("authors", String.join(", ", getDescription().getAuthors()))
        );
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        IoSocket.getSocket().disconnect();
    }

    private void initialiseConfigs() {
        if(!Files.exists(getDataFolder().toPath())) {
            try {
                Files.createDirectories(getDataFolder().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.lang = new Lang();
            this.lang.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            configSystem = new Config();
            configSystem.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main.getInstance().getInternalLang().send(adventure.console(), "console.getapi");
        if (configSystem.getAPIKEY().isEmpty() || configSystem.getAPIKEY().equals("KEY HER")) {
            Main.getInstance().getInternalLang().send(adventure.console(), "console.apikeymissing");
            APIKEY = null;
            return;
        }
        Main.getInstance().getInternalLang().send(adventure.console(), "console.apikeyfound");
        APIKEY = configSystem.getAPIKEY();
    }

    public void reload() {
        long timestampBeforeLoad = System.currentTimeMillis();
        Main.getInstance().getInternalLang().send(adventure.console(), "console.reloading", Placeholder.unparsed("version", getDescription().getVersion()));
        try {
            this.internalLang = new InternalLang();
            this.internalLang.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Main.getInstance().getInternalLang().send(adventure.console(), "console.config");
        initialiseConfigs();

        Main.getInstance().getInternalLang().send(adventure.console(), "console.webSocket");
        if(IoSocket.getSocket().connected()) {
            IoSocket.getSocket().disconnect();
        }
        IoSocket.connectSocket();

        Main.getInstance().getInternalLang().send(adventure.console(), "console.reloaded",
                Placeholder.unparsed("version", getDescription().getVersion()),
                Placeholder.unparsed("time", String.valueOf(System.currentTimeMillis() - timestampBeforeLoad))
        );
    }

    private void initialiseHooks() {
        IHook[] hooks = {
                new SkriptHook()
        };
        for (IHook hook : hooks) {
            HOOKS.put(hook.getEnum(), hook.init(this));
        }
    }

    public static boolean isHookInitialised(Hook paramHook) {
        return HOOKS.getOrDefault(paramHook, Boolean.FALSE);
    }

    public File getFile() {
        return super.getFile();
    }
}