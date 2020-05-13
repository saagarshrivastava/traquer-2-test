package com.test.traquer2.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
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

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.test.traquer2.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.test.traquer2.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.test.traquer2.domain.User.class.getName());
            createCache(cm, com.test.traquer2.domain.Authority.class.getName());
            createCache(cm, com.test.traquer2.domain.User.class.getName() + ".authorities");
            createCache(cm, com.test.traquer2.domain.Session.class.getName());
            createCache(cm, com.test.traquer2.domain.Schedule.class.getName());
            createCache(cm, com.test.traquer2.domain.Candidate.class.getName());
            createCache(cm, com.test.traquer2.domain.Location.class.getName());
            createCache(cm, com.test.traquer2.domain.ProctoringInstance.class.getName());
            createCache(cm, com.test.traquer2.domain.SessionBreaks.class.getName());
            createCache(cm, com.test.traquer2.domain.Incident.class.getName());
            createCache(cm, com.test.traquer2.domain.CategoryInstance.class.getName());
            createCache(cm, com.test.traquer2.domain.SubcategoryInstance.class.getName());
            createCache(cm, com.test.traquer2.domain.MajorIncident.class.getName());
            createCache(cm, com.test.traquer2.domain.MajorIncidentSource.class.getName());
            createCache(cm, com.test.traquer2.domain.Proctor.class.getName());
            createCache(cm, com.test.traquer2.domain.SupportInstance.class.getName());
            createCache(cm, com.test.traquer2.domain.SupportPerson.class.getName());
            createCache(cm, com.test.traquer2.domain.DeliveryType.class.getName());
            createCache(cm, com.test.traquer2.domain.ExamType.class.getName());
            createCache(cm, com.test.traquer2.domain.DeliveryStatus.class.getName());
            createCache(cm, com.test.traquer2.domain.FailureStage.class.getName());
            createCache(cm, com.test.traquer2.domain.Category.class.getName());
            createCache(cm, com.test.traquer2.domain.Subcategory.class.getName());
            createCache(cm, com.test.traquer2.domain.Exam.class.getName());
            createCache(cm, com.test.traquer2.domain.Region.class.getName());
            createCache(cm, com.test.traquer2.domain.ExamBackend.class.getName());
            createCache(cm, com.test.traquer2.domain.OfferType.class.getName());
            createCache(cm, com.test.traquer2.domain.CloudInstance.class.getName());
            createCache(cm, com.test.traquer2.domain.CloudRegion.class.getName());
            createCache(cm, com.test.traquer2.domain.Offer.class.getName());
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
