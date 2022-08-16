package pl.szczurowsky.mcantiproxy;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bungee.LiteBungeeFactory;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bungee.YamlBungeeConfigurer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import pl.szczurowsky.mcantiproxy.cache.CacheManager;
import pl.szczurowsky.mcantiproxy.commands.AntiProxyCommandsBuilder;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;
import pl.szczurowsky.mcantiproxy.listener.PreLoginHandler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

public class BungeePlugin extends Plugin {

    private PluginConfig config;
    private MessagesConfig messagesConfig;
    private LiteCommands<CommandSender> liteCommands;
    private CacheManager cacheManager;

    @Override
    public void onEnable() {
        LocalDateTime starting = LocalDateTime.now();
        Logger logger = this.getLogger();

        logger.info("Enabling anti-proxy plugin");

        registerConfigs();
        logger.info("Configs registered");

        this.cacheManager = new CacheManager(config.getCacheExpirationTime());
        logger.info("Cache manager initialized");

        registerEvents();
        logger.info("Events registered");

        registerCommands();
        logger.info("Commands registered");

        logger.info("Successfully enabled plugin in " + ChronoUnit.MILLIS.between(starting, LocalDateTime.now()) + "ms");
    }

    @Override
    public void onDisable() {
        this.liteCommands.getCommandService().getPlatform().unregisterAll();
    }

    private void registerEvents() {
        getProxy().getPluginManager().registerListener(this, new PreLoginHandler(config, messagesConfig, cacheManager));
    }

    private void registerConfigs() {
        this.config = ConfigManager.create(PluginConfig.class, (config) -> {
            config.withConfigurer(new YamlBungeeConfigurer());
            config.withBindFile(new File(getDataFolder(), "config.yml"));
            config.saveDefaults();
            config.load(true);
        });

        this.messagesConfig = ConfigManager.create(MessagesConfig.class, (config) -> {
            config.withConfigurer(new YamlBungeeConfigurer());
            config.withBindFile(new File(getDataFolder(), "message.yml"));
            config.saveDefaults();
            config.load(true);
        });
    }

    private void registerCommands() {
        this.liteCommands = AntiProxyCommandsBuilder.applyOn(LiteBungeeFactory.builder(this), this.config, this.messagesConfig)
                .forwardingRegister();
    }

}
