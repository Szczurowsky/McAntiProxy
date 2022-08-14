package pl.szczurowsky.mcantiproxy.commands.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.scheme.Scheme;
import org.bukkit.command.CommandSender;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.util.ColorUtil;

import java.util.List;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    private final MessagesConfig messagesConfig;

    public InvalidUsage(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Scheme scheme) {
        List<String> schemes = scheme.getSchemes();

        if (schemes.size() == 1) {
            sender.sendMessage(ColorUtil.format(messagesConfig.getInvalidUsage() + " " + schemes.get(0)));
            return;
        }

        sender.sendMessage(ColorUtil.format(messagesConfig.getInvalidUsage()));
        for (String sch : schemes) {
            sender.sendMessage((ColorUtil.format("&8>> &6" + sch)));
        }
    }

}
