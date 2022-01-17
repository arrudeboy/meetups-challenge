package com.santander.tecnologia.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class CacheManagerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManagerTask.class);

    @Autowired
    CacheManager cacheManager;

    @Scheduled(cron = "${scheduler.task.clear.caches.cron-exp}")
    public void clearCaches() {
        LOGGER.info("Started cronjob clearCaches...");
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        LOGGER.info("Finished cronjob clearCaches.");
    }

}
