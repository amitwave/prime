package com.natwest.prime.cache;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class TaskCacheListener implements CacheEventListener<Long, Boolean> {
    @Override
    public void onEvent(CacheEvent<? extends Long, ? extends Boolean> cacheEvent) {
        log.info("Event '{}' fired for key '{}' with value {}", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getNewValue());
    }
}
