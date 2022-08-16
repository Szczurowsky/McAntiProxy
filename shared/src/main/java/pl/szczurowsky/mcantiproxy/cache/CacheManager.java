package pl.szczurowsky.mcantiproxy.cache;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    /**
     * Cache map with key - ip address and value - timestamp
     */
    protected Map<String, LocalDateTime> cache = new HashMap<>();

    protected Map<String, Boolean> cacheResult = new HashMap<>();

    protected final Duration cacheTime;

    public CacheManager(Duration cacheTime) {
        this.cacheTime = cacheTime;
    }

    /**
     * Check if ip address is in cache and is cache expired
     * @param ip ip address
     * @return true if ip address is in cache
     */
    public boolean isCached(String ip) {
        if (cache.containsKey(ip)) {
            LocalDateTime timestamp = cache.get(ip);
            if (LocalDateTime.now().isAfter(timestamp)) {
                cache.remove(ip);
                return false;
            }
            if (cacheResult.containsKey(ip)) {
                return cacheResult.get(ip);
            }
            return false;
        }
        return false;
    }

    public void addToCache(String ip, boolean result) {
        cache.put(ip, LocalDateTime.now().plus(this.cacheTime));
        cacheResult.put(ip, result);
    }

}
