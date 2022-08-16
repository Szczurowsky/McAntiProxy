package pl.szczurowsky.mcantiproxy.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.Arrays;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessagesConfig extends OkaeriConfig {


    @Comment("Message when command is used wrongly")
    private String invalidUsage = "&8[ &6&l!&8 ] &7Bad command usage";

    @Comment("Message when player is not allowed to use command")
    private String noPermission = "&8[ &4&l✖&8 ] &cYou've no permission to use this command";

    @Comment("Message when config is reloaded successfully")
    private String reloadSuccess = "&8[ &a&l✔&8 ] &aConfigs reloaded";

    @Comment("Message when config's reload failed")
    private String reloadFailure = "&8[ &4&l✖&8 ] &cConfigs reload failed";

    @Comment("Help menu")
    private List<String> help = Arrays.asList(
            "&7●●●●●●● &6&lAntiProxy &7●●●●●●●",
            "&6/ap reload - &7Reload configs",
            "&7●●●●●●● &6&lAntiProxy &7●●●●●●●"
    );

    @Comment("Message when blocking player connection")
    private String kickMessage = "&c Connection blocked \n &7We detected VPN/Proxy connection. \n &7Please contact an admin. \n &7 Your ip: &c{ip}";

    public List<String> getHelp() {
        return help;
    }

    public String getInvalidUsage() {
        return invalidUsage;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getReloadSuccess() {
        return reloadSuccess;
    }

    public String getReloadFailure() {
        return reloadFailure;
    }

    public String getKickMessage() {
        return kickMessage;
    }
}
