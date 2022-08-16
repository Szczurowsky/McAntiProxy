package pl.szczurowsky.mcantiproxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.rollczi.litecommands.velocity.LiteVelocityFactory;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.jetbrains.annotations.NotNull;
import pl.szczurowsky.mcantiproxy.cache.CacheManager;
import pl.szczurowsky.mcantiproxy.commands.AntiProxyCommandsBuilder;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;
import pl.szczurowsky.mcantiproxy.listener.PreLoginHandler;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Plugin(
        id = "mcantiproxy",
        name = "McAntiProxy-Velocity",
        authors = "Szczurowsky",
        version = "1.1",
        url = "https://szczurowsky.pl/"
)
public class VelocityPlugin {

    private PluginConfig config;
    private MessagesConfig messagesConfig;
    private CacheManager cacheManager;

    /**
     * Velocity plugin constructor.
     */
    private final Logger logger;
    private final Path dataDirectory;
    private final ProxyServer proxyServer;
    private final PluginDescription pluginDescription;

    @Inject
    public VelocityPlugin(Logger logger, @DataDirectory Path dataDirectory, ProxyServer proxyServer, PluginDescription pluginDescription) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.proxyServer = proxyServer;
        this.pluginDescription = pluginDescription;
    }

    @Subscribe
    public void onProxyInitialization(@NotNull ProxyInitializeEvent event) {
        LocalDateTime starting = LocalDateTime.now();

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

    private void registerCommands() {
        AntiProxyCommandsBuilder.applyOn(LiteVelocityFactory.builder(proxyServer), this.config, this.messagesConfig)
                .forwardingRegister();
    }

    private void registerEvents() {
        this.proxyServer.getEventManager().register(this, new PreLoginHandler(config, messagesConfig, cacheManager));
    }

    private void registerConfigs() {
        this.config = ConfigManager.create(PluginConfig.class, (config) -> {
            config.withConfigurer(new YamlSnakeYamlConfigurer());
            config.withBindFile(new File(dataDirectory.toFile(), "config.yml"));
            config.saveDefaults();
            config.load(true);
        });

        this.messagesConfig = ConfigManager.create(MessagesConfig.class, (config) -> {
            config.withConfigurer(new YamlSnakeYamlConfigurer());
            config.withBindFile(new File(dataDirectory.toFile(), "messages.yml"));
            config.saveDefaults();
            config.load(true);
        });
    }

}
