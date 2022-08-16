package pl.szczurowsky.mcantiproxy.commands;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.command.permission.LitePermissions;
import dev.rollczi.litecommands.scheme.Scheme;
import org.jetbrains.annotations.NotNull;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;

public class AntiProxyCommandsBuilder<T> {

    private final LiteCommandsBuilder<T> commandsBuilder;

    private AntiProxyCommandsBuilder(LiteCommandsBuilder<T> commandsBuilder) {
        this.commandsBuilder = commandsBuilder;
    }

    public static <T> AntiProxyCommandsBuilder<T> applyOn(@NotNull LiteCommandsBuilder<T> originCommandsBuilder,
                                                          @NotNull PluginConfig pluginConfig,
                                                          @NotNull MessagesConfig messagesConfig) {
        LiteCommandsBuilder<T> fancyCommandsBuilder = originCommandsBuilder
                .typeBind(PluginConfig.class, () -> pluginConfig)
                .typeBind(MessagesConfig.class, () -> messagesConfig)
                .redirectResult(LitePermissions.class, String.class, permissions -> messagesConfig.noPermissionMessage)
                .redirectResult(Scheme.class, String.class, scheme -> {
                    String delimiter = scheme.getSchemes().size() == 1 ? " " : "\n";
                    return messagesConfig.invalidUsageMessage + delimiter + String.join(delimiter, scheme.getSchemes());
                });
        return new AntiProxyCommandsBuilder<>(fancyCommandsBuilder);
    }

    public LiteCommands<T> forwardingRegister() {
        return this.commandsBuilder
                .command(AntiProxyCommand.class)
                .register();
    }
}
