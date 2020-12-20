package com.natwest.prime.apache.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class TaskCacheListener implements CacheEventListener<Integer, Boolean> {
    @Override
    public void onEvent(CacheEvent<? extends Integer, ? extends Boolean> cacheEvent) {
        log.info("Event '{}' fired for key '{}' with value {}", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getNewValue());
    }
}
