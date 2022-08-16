package pl.szczurowsky.mcantiproxy.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Variable("API-TOKEN")
    @Comment("Proxycheck.io token")
    public String token = "Put your proxycheck.io token here";

    @Variable("IPS-WHITELIST")
    @Comment("List of IPs which are allowed to connect to server")
    public List<String> whitelistedIps = Collections.singletonList("127.0.0.1");

    @Variable("CACHE-EXPRIE")
    @Comment("Cache expiration time")
    public Duration cacheExpirationTime = Duration.ofSeconds(15);
}
