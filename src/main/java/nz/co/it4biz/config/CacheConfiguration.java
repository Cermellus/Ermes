package nz.co.it4biz.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(nz.co.it4biz.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(nz.co.it4biz.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CallLogAction.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CallLogLine.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CallLog.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CreditReason.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CreditReferenceType.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CreditRequestLine.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CreditRequest.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CreditRequestStatus.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.CreditReturnType.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Prospect.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.Role.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.SalesPerson.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.ErmesUser.class.getName(), jcacheConfiguration);
            cm.createCache(nz.co.it4biz.domain.AppUser.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
