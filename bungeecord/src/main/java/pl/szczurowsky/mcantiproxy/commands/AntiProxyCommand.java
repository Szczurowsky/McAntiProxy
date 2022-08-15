package pl.szczurowsky.mcantiproxy.commands;

import dev.rollczi.litecommands.command.amount.Required;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.platform.LiteSender;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;
import pl.szczurowsky.mcantiproxy.util.ColorUtil;

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
    public void help(LiteSender liteSender) {
        ColorUtil.formatList(messagesConfig.getHelp()).forEach(liteSender::sendMessage);
    }

    @Execute(route = "reload")
    @Required(0)
    public void reload(LiteSender liteSender) {
        config.load();
        messagesConfig.load();
        liteSender.sendMessage(ColorUtil.format(messagesConfig.getReloadSuccess()));
    }

}
