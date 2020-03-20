package de.wirvsvirus.app.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, de.wirvsvirus.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, de.wirvsvirus.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, de.wirvsvirus.app.domain.User.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Authority.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.User.class.getName() + ".authorities");
            createCache(cm, de.wirvsvirus.app.domain.Skill.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Skill.class.getName() + ".userProfiles");
            createCache(cm, de.wirvsvirus.app.domain.Skill.class.getName() + ".projects");
            createCache(cm, de.wirvsvirus.app.domain.Skill.class.getName() + ".tasks");
            createCache(cm, de.wirvsvirus.app.domain.UserProfile.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.UserProfile.class.getName() + ".skills");
            createCache(cm, de.wirvsvirus.app.domain.UserProfile.class.getName() + ".projects");
            createCache(cm, de.wirvsvirus.app.domain.Challenge.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Challenge.class.getName() + ".categories");
            createCache(cm, de.wirvsvirus.app.domain.Challenge.class.getName() + ".ideas");
            createCache(cm, de.wirvsvirus.app.domain.Category.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Category.class.getName() + ".challenges");
            createCache(cm, de.wirvsvirus.app.domain.Idea.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Idea.class.getName() + ".projects");
            createCache(cm, de.wirvsvirus.app.domain.Idea.class.getName() + ".challenges");
            createCache(cm, de.wirvsvirus.app.domain.Project.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Project.class.getName() + ".userProfiles");
            createCache(cm, de.wirvsvirus.app.domain.Project.class.getName() + ".skills");
            createCache(cm, de.wirvsvirus.app.domain.Project.class.getName() + ".tasks");
            createCache(cm, de.wirvsvirus.app.domain.Task.class.getName());
            createCache(cm, de.wirvsvirus.app.domain.Task.class.getName() + ".skills");
            createCache(cm, de.wirvsvirus.app.domain.Task.class.getName() + ".projects");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
