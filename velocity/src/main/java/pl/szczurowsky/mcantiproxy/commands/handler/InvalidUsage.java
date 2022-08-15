package pl.szczurowsky.mcantiproxy.commands.handler;

import com.velocitypowered.api.command.CommandSource;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.scheme.Scheme;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;

import java.util.List;

public class InvalidUsage implements InvalidUsageHandler<CommandSource> {

    private final MessagesConfig messagesConfig;

    public InvalidUsage(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    @Override
    public void handle(CommandSource sender, LiteInvocation invocation, Scheme scheme) {
        List<String> schemes = scheme.getSchemes();

        if (schemes.size() == 1) {
            sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize(messagesConfig.getInvalidUsage() + " " + schemes.get(0)));
            return;
        }

        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize(messagesConfig.getInvalidUsage()));
        for (String sch : schemes) {
            sender.sendMessage((LegacyComponentSerializer.legacyAmpersand().deserialize("&8>> &6" + sch)));
        }
    }

}
