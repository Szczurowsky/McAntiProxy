package pl.szczurowsky.mcantiproxy;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bungee.LiteBungeeFactory;
import eu.okaeri.configs.yaml.bungee.YamlBungeeConfigurer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import pl.szczurowsky.mcantiproxy.cache.CacheManager;
import pl.szczurowsky.mcantiproxy.commands.AntiProxyCommandsBuilder;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;
import pl.szczurowsky.mcantiproxy.configs.factory.ConfigFactory;
import pl.szczurowsky.mcantiproxy.listener.PreLoginHandler;

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

        this.cacheManager = new CacheManager(config.cacheExpirationTime);
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
        ConfigFactory configFactory = new ConfigFactory(this.getDataFolder(), YamlBungeeConfigurer::new);
        this.config = configFactory.produceConfig(PluginConfig.class, "config.yml");
        this.messagesConfig = configFactory.produceConfig(MessagesConfig.class, "message.yml");
    }

    private void registerCommands() {
        this.liteCommands = AntiProxyCommandsBuilder.applyOn(LiteBungeeFactory.builder(this), this.config, this.messagesConfig)
                .forwardingRegister();
    }

}
