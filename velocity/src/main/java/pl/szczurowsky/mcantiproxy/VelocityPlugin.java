package pl.szczurowsky.mcantiproxy;


import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.velocity.LiteVelocityFactory;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.jetbrains.annotations.NotNull;
import pl.szczurowsky.mcantiproxy.commands.AntiProxyCommand;
import pl.szczurowsky.mcantiproxy.commands.handler.InvalidUsage;
import pl.szczurowsky.mcantiproxy.commands.handler.PermissionMessageHandler;
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
        url = "https://szczurowsky.pl/"
)
public class VelocityPlugin {

    private PluginConfig config;
    private MessagesConfig messagesConfig;

    /**
     * Velocity plugin constructor.
     */
    private final Logger logger;
    private final Path dataDirectory;
    private final ProxyServer proxyServer;
    private final PluginDescription pluginDescription;
    private LiteCommands liteCommands;

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

        registerEvents();
        logger.info("Events registered");

        registerCommands();
        logger.info("Commands registered");

        logger.info("Successfully enabled plugin in " + ChronoUnit.MILLIS.between(starting, LocalDateTime.now()) + "ms");
    }


    private void registerCommands() {
        this.liteCommands = LiteVelocityFactory.builder(proxyServer)
                .permissionHandler(new PermissionMessageHandler(messagesConfig))
                .invalidUsageHandler(new InvalidUsage(messagesConfig))

                .typeBind(PluginConfig.class, () -> this.config)
                .typeBind(MessagesConfig.class, () -> this.messagesConfig)

                .command(AntiProxyCommand.class)

                .register();
    }

    private void registerEvents() {
        this.proxyServer.getEventManager().register(this, new PreLoginHandler(config, messagesConfig));
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
