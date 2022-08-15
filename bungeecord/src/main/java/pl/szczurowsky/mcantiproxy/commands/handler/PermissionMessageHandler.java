package pl.szczurowsky.mcantiproxy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.LitePermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import net.md_5.bungee.api.CommandSender;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.util.ColorUtil;

public class PermissionMessageHandler implements PermissionHandler<CommandSender> {

    private final MessagesConfig messagesConfig;

    public PermissionMessageHandler(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation liteInvocation, LitePermissions litePermissions) {
        commandSender.sendMessage(ColorUtil.format(messagesConfig.getNoPermission()));
    }
}
