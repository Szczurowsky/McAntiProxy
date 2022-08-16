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
    public String invalidUsageMessage = "&8[ &6&l!&8 ] &7Bad command usage";

    @Comment("Message when player is not allowed to use command")
    public String noPermissionMessage = "&8[ &4&l✖&8 ] &cYou've no permission to use this command";

    @Comment("Message when config is reloaded successfully")
    public String reloadSuccessMessage = "&8[ &a&l✔&8 ] &aConfigs reloaded";

    @Comment("Message when config's reload failed")
    public String reloadFailureMessage = "&8[ &4&l✖&8 ] &cConfigs reload failed";

    @Comment("Help menu")
    public List<String> helpMessage = Arrays.asList(
            "&7●●●●●●● &6&lAntiProxy &7●●●●●●●",
            "&6/ap reload - &7Reload configs",
            "&7●●●●●●● &6&lAntiProxy &7●●●●●●●");

    @Comment("Message when blocking player connection")
    public String kickMessage = "&cConnection blocked\n"
            + "&7We detected VPN/Proxy connection.\n"
            + "&7Please contact an admin.\n"
            + "&7 Your ip: &c{ip}";
}
