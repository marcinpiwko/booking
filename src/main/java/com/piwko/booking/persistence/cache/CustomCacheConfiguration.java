package com.piwko.booking.persistence.cache;

import lombok.RequiredArgsConstructor;
import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CustomCacheConfiguration {

    private final CacheProperties cacheProperties;

    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean() {
        return new EhCacheManagerFactoryBean();
    }

    @Bean
    public CacheManager cacheManager() {
        for (CacheRegion cacheRegion : CacheRegion.values()) {
            Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(new Cache(commonCacheConfiguration(cacheRegion.name())));
        }
        return new EhCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean().getObject()));
    }

    private CacheConfiguration commonCacheConfiguration(String cacheName) {
        return new CacheConfiguration()
        .name(cacheName)
        .eternal(cacheProperties.getEternal())
        .timeToLiveSeconds(cacheProperties.getMaxTimeToLive())
        .timeToIdleSeconds(cacheProperties.getMaxTimeToIdle())
        .memoryStoreEvictionPolicy(cacheProperties.getMemoryStorePolicy())
        .maxEntriesLocalHeap(cacheProperties.getMaxEntries());
    }
}
