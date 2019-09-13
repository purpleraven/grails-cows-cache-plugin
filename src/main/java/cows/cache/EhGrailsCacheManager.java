package cows.cache;

import org.grails.plugin.cache.GrailsCacheManager;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EhGrailsCacheManager implements GrailsCacheManager {
    protected CacheManager cacheManager;
    protected final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            // check the EhCache cache again (in case the cache was added at runtime)
            Ehcache ehcache = cacheManager.getEhcache(name);
            if (ehcache == null) {
                synchronized (this){
                    ehcache = cacheManager.getEhcache(name);
                    if (ehcache == null){
                        // create a new one based on defaults
                        cacheManager.addCache(name);
                        ehcache = cacheManager.getEhcache(name);
                    }
                }
            }

            cache = new EhCacheCache(ehcache);
            addCache(cache);
        }
        return cache;
    }

    public Ehcache getNativeCache(String name){
        EhCacheCache cacheCache = (EhCacheCache) getCache(name);
        return cacheCache.getNativeCache();
    }

    protected void addCache(Cache cache) {
        cacheMap.put(cache.getName(), cache);
    }

    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(cacheMap.keySet());
    }

    public boolean cacheExists(String name) {
        return getCacheNames().contains(name);
    }

    public boolean destroyCache(String name) {
        synchronized (cacheMap) {
            cacheManager.removeCache(name);
            cacheMap.remove(name);
        }
        return true;
    }

    /**
     * Set the backing EhCache {@link net.sf.ehcache.CacheManager}.
     */
    public void setCacheManager(net.sf.ehcache.CacheManager manager) {
        cacheManager = manager;
    }
}
