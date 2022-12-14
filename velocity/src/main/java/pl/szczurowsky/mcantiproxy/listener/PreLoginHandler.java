package pl.szczurowsky.mcantiproxy.listener;

import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.json.JSONObject;
import pl.szczurowsky.mcantiproxy.cache.CacheManager;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;
import pl.szczurowsky.mcantiproxy.util.HttpUtil;

import java.net.HttpURLConnection;

public class PreLoginHandler {

    private final PluginConfig config;
    private final MessagesConfig messagesConfig;
    private final CacheManager cacheManager;

    public PreLoginHandler(PluginConfig config, MessagesConfig messagesConfig, CacheManager cacheManager) {
        this.config = config;
        this.messagesConfig = messagesConfig;
        this.cacheManager = cacheManager;
    }

    @Subscribe(order = PostOrder.FIRST)
    public EventTask onPreLogin(PreLoginEvent event) {
        return EventTask.async(() -> {
            String token = config.token;
            String ip = event.getConnection().getRemoteAddress().getAddress().getHostAddress();
            if (cacheManager.isCached(ip)) {
                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(LegacyComponentSerializer.legacyAmpersand().deserialize(messagesConfig.kickMessage.replace("{ip}", ip))));
                return;
            }
            try {
                HttpURLConnection connection = HttpUtil.prepareConnection(HttpUtil.createConnection("https://proxycheck.io/v2/" + ip + "?key=" + token + "&vpn=1"));
                if (connection.getResponseCode() != 200)
                    return;
                JSONObject response = new JSONObject(HttpUtil.readSourceCode(connection));
                JSONObject data = response.getJSONObject(ip);
                if (!data.has("proxy"))
                    return;
                if (data.getString("proxy").equals("yes") && !config.whitelistedIps.contains(ip)) {
                    event.setResult(PreLoginEvent.PreLoginComponentResult.denied(LegacyComponentSerializer.legacyAmpersand().deserialize(messagesConfig.kickMessage.replace("{ip}", ip))));
                    cacheManager.addToCache(ip, true);
                    return;
                }
                cacheManager.addToCache(ip, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
