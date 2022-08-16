package pl.szczurowsky.mcantiproxy.commands;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import eu.okaeri.configs.exception.OkaeriException;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;

@Section(route = "antiproxy")
public class AntiProxyCommand {

    private final MessagesConfig messagesConfig;
    private final PluginConfig config;

    public AntiProxyCommand(MessagesConfig messagesConfig, PluginConfig config) {
        this.messagesConfig = messagesConfig;
        this.config = config;
    }

    @Execute(required = 0)
    public String help() {
        return String.join("\n", messagesConfig.getHelp());
    }

    @Execute(route = "reload")
    public String reload() {
        try {
            config.load();
            messagesConfig.load();
            return messagesConfig.getReloadSuccess();
        } catch (OkaeriException exception) {
            return messagesConfig.getReloadFailure();
        }
    }
}
