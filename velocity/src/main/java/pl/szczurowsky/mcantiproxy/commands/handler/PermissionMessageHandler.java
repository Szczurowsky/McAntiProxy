package pl.szczurowsky.mcantiproxy.commands.handler;

import com.velocitypowered.api.command.CommandSource;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.LitePermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;

public class PermissionMessageHandler implements PermissionHandler<CommandSource> {

    private final MessagesConfig messagesConfig;

    public PermissionMessageHandler(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    @Override
    public void handle(CommandSource commandSender, LiteInvocation liteInvocation, LitePermissions litePermissions) {
        commandSender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(messagesConfig.getNoPermission()));
    }
}
