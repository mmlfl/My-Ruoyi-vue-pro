package cn.iocoder.lfl.framework.redis.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import static cn.iocoder.lfl.framework.redis.config.LflRedisAutoConfiguration.buildRedisSerializer;

@AutoConfiguration
@EnableConfigurationProperties({LflCacheProperties.class, CacheProperties.class})
public class LflCacheAutoConfiguration {

    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //配置 存入缓存时的是否有前缀名,以及前缀名是什么
        config = config.computePrefixWith(cacheName -> {
            String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
            if(StringUtils.hasText(keyPrefix)){
                keyPrefix = keyPrefix.lastIndexOf(StrUtil.COLON) == -1?keyPrefix+StrUtil.COLON : keyPrefix;
                return keyPrefix + cacheName + StrUtil.COLON;
            }
            return cacheName + StrUtil.COLON;
        });
        //设置使用json序列化格式
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(buildRedisSerializer())
        );

        //设置cacheProperties的Redis属性
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if(redisProperties.getTimeToLive() != null){
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if(!redisProperties.isCacheNullValues()){
            config = config.disableCachingNullValues();
        }
        if(!redisProperties.isUseKeyPrefix()){
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
