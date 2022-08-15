package pl.szczurowsky.mcantiproxy.commands;

import com.velocitypowered.api.command.CommandSource;
import dev.rollczi.litecommands.command.amount.Required;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;

@Section(route = "antiproxy", aliases = "ap")
@Permission("antiproxy.admin")
public class AntiProxyCommand {

    private final MessagesConfig messagesConfig;
    private final PluginConfig config;

    public AntiProxyCommand(MessagesConfig messagesConfig, PluginConfig config) {
        this.messagesConfig = messagesConfig;
        this.config = config;
    }

    @Execute()
    @Required(0)
    public void help(CommandSource velocitySender) {
        messagesConfig.getHelp().forEach(message -> {
            velocitySender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        });
    }

    @Execute(route = "reload")
    @Required(0)
    public void reload(CommandSource velocitySender) {
        config.load();
        messagesConfig.load();
        velocitySender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(messagesConfig.getReloadSuccess()));
    }

}
