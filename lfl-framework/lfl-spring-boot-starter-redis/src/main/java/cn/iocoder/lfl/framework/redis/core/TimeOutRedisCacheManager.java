package cn.iocoder.lfl.framework.redis.core;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

public class TimeOutRedisCacheManager extends RedisCacheManager {

    private static final String SPLIT = "#";

    public TimeOutRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig){
        if(StrUtil.isEmpty(name)){
            return super.createRedisCache(name, cacheConfig);
        }
        // 如果使用 # 分隔，大小不为 2，则说明不使用自定义过期时间
        String[] names = StrUtil.splitToArray(name, SPLIT);
        if(names.length != 2){
            return super.createRedisCache(name, cacheConfig);
        }

        if (cacheConfig != null) {
            String ttlStr = StrUtil.subBefore(names[1],StrUtil.COLON,false);
            names[1] = StrUtil.subAfter(names[1],ttlStr,false);
            //解析时间
            Duration duration = parseDuration(ttlStr);
            cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(names[0]+names[1],cacheConfig);
    }

    /**
     * 获取过期时间
     * @param ttlStr
     * @return
     */
    private Duration parseDuration(String ttlStr) {
        String timeUnit = StrUtil.subSuf(ttlStr,-1);
        switch(timeUnit){
            case "d":
                return Duration.ofDays(removeDurationSuffix(ttlStr));
            case "m":
                return Duration.ofMinutes(removeDurationSuffix(ttlStr));
            case "h":
                return Duration.ofHours(removeDurationSuffix(ttlStr));
            case "s":
                return Duration.ofSeconds(removeDurationSuffix(ttlStr));
            default:
                return Duration.ofSeconds(Long.parseLong(ttlStr));
        }
    }

    /**
     * 移除掉ttlstr后面的多余后缀,变为long类型返回
     * @param ttlStr
     * @return
     */
    private Long removeDurationSuffix(String ttlStr) {
        return NumberUtil.parseLong(StrUtil.sub(ttlStr,0,ttlStr.length()-1));
    }
}
