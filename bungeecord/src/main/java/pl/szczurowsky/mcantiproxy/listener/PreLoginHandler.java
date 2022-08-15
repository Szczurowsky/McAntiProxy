package pl.szczurowsky.mcantiproxy.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.json.JSONObject;
import pl.szczurowsky.mcantiproxy.cache.CacheManager;
import pl.szczurowsky.mcantiproxy.configs.MessagesConfig;
import pl.szczurowsky.mcantiproxy.configs.PluginConfig;
import pl.szczurowsky.mcantiproxy.util.ColorUtil;
import pl.szczurowsky.mcantiproxy.util.HttpUtil;

import java.net.HttpURLConnection;

public class PreLoginHandler implements Listener {

    private final PluginConfig config;
    private final MessagesConfig messagesConfig;
    private final CacheManager cacheManager;

    public PreLoginHandler(PluginConfig config, MessagesConfig messagesConfig, CacheManager cacheManager) {
        this.config = config;
        this.messagesConfig = messagesConfig;
        this.cacheManager = cacheManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(PreLoginEvent event) {
        String token = config.getToken();
        String ip = (event.getConnection().getSocketAddress().toString().split(":")[0]).substring(1);
        if (cacheManager.isCached(ip)) {
            event.setCancelled(true);
            event.setCancelReason(TextComponent.fromLegacyText(ColorUtil.format(messagesConfig.getKickMessage().replace("{ip}", ip))));
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
            if (data.getString("proxy").equals("yes") && !config.getWhitelistedIps().contains(ip)) {
                event.setCancelled(true);
                event.setCancelReason(TextComponent.fromLegacyText(ColorUtil.format(messagesConfig.getKickMessage().replace("{ip}", ip))));
                cacheManager.addToCache(ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
