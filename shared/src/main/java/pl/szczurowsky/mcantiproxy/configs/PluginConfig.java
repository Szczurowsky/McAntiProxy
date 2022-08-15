package pl.szczurowsky.mcantiproxy.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.Collections;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Variable("API-TOKEN")
    @Comment("Proxycheck.io token")
    private String token = "Put your proxycheck.io token here";

    @Variable("IPS-WHITELIST")
    @Comment("List of IPs which are allowed to connect to server")
    private List<String> whitelistedIps = Collections.singletonList("127.0.0.1");

    @Variable("CACHE-EXPRIE")
    @Comment("Cache expiration time in seconds")
    private Long cacheExpirationTime = 60 * 15L;

    public String getToken() {
        return token;
    }

    public List<String> getWhitelistedIps() {
        return whitelistedIps;
    }

    public Long getCacheExpirationTime() {
        return cacheExpirationTime;
    }
}
