package pl.szczurowsky.mcantiproxy.cache;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CacheManager {

    /**
     * Cache map with key - ip address and value - timestamp
     */
    protected HashMap<String, LocalDateTime> cache = new HashMap<>();

    protected HashMap<String, Boolean> cacheResult = new HashMap<>();


    protected final long cacheTime;

    public CacheManager(long cacheTime) {
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
        cache.put(ip, LocalDateTime.now().plusSeconds(cacheTime));
        cacheResult.put(ip, result);
    }

}
