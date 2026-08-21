package org.nova.platform.common.database.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Description: 缓存配置:
 * redis在serviceImpl里使用方式
 * 1、Cacheable 的用法
 *     缓存有数据时，从缓存获取；没有数据时，执行方法，并将返回值保存到缓存中
 *  @Cacheable 一般在查询中使用
 *     1) cacheNames 指定缓存区，没有配置使用@CacheConfig指定的缓存区
 *     2) key 指定缓存区的key
 *     3) 注解的值使用SpEL表达式（eq ==  lt <  le <=   gt >  ge >=）
 * 1.1、时效性缓存(缓存24小时)
 *     @Cacheable(value = RedisCongif.CACHE_NAME_HOURS_24)
 * 1.2、缓存到指定key
 *     @Cacheable(key="'list'")
 * 1.3、condition 满足条件缓存数据
 *     @Cacheable(key = "#id", condition = "#number ge 20") // >= 20
 * 1.4、unless 满足条件时否决缓存数据
 *     @Cacheable(key = "#id", unless = "#number lt 20") // < 20
 * 2、CachePut 的用法
 *     一定会执行方法，并将返回值保存到缓存中
 *  @CachePut 一般在新增和修改中使用
 * 2.1、缓存到指定key
 *     @CachePut(key = "#user.id")
 * 2.2、condition 满足条件缓存数据
 *     @CachePut(key = "#user.id", condition = "#user.age ge 20")
 * 3、CacheEvict 的用法
 *     CacheEvict 来删除缓存，@CacheEvict 就是一个触发器，在每次调用被它注解的方法时，就会触发删除它指定的缓存的动作
 * 3.1、根据key删除缓存区中的数据
 *     @CacheEvict(key = "#id")
 * 3.2、根据条件配置
 *     allEntries = true ：删除整个缓存区的所有值，此时指定的key无效
 *     beforeInvocation = true ：默认false，表示调用方法之后删除缓存数据；true时，在调用之前删除缓存数据(如方法出现异常)
 *     @CacheEvict(key = "#id", allEntries = true)
 *
 * @author 张岳
 * @date 2021/9/25 11:52
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 分割符
     */
    public static final String DECOLLATOR = ":";

    /**
     * 应用前缀
     */
    public static final String APP_PREFIX = "redis";

    /**
     * 缓存名前缀
     */
    public static final String CACHE_NAMES_PREFIX = APP_PREFIX + DECOLLATOR + "cacheNames" + DECOLLATOR;

    /**
     * 永不过期的缓存名
     */
    public static final String CACHE_NAME_FOREVER = CACHE_NAMES_PREFIX + "forever";

    /**
     * 10分钟有效期的缓存名
     */
    public static final String CACHE_NAME_MINUTES_10 = CACHE_NAMES_PREFIX + "minutes-10";

    /**
     * 30分钟有效期的缓存名
     */
    public static final String CACHE_NAME_MINUTES_30 = CACHE_NAMES_PREFIX + "minutes-30";

    /**
     * 1个小时有效期的缓存名
     */
    public static final String CACHE_NAME_HOURS_01 = CACHE_NAMES_PREFIX + "hours-1";

    /**
     * 24小时有效期的缓存名
     */
    public static final String CACHE_NAME_HOURS_24 = CACHE_NAMES_PREFIX + "hours-24";

    /**
     * 12小时有效期的缓存名
     */
    public static final String CACHE_NAME_HOURS_12 = CACHE_NAMES_PREFIX + "hours-12";

    /**
     * 30天有效期的缓存名
     */
    public static final String CACHE_NAME_DAYS_30 = CACHE_NAMES_PREFIX + "days-30";

    /**
     * 选择redis作为默认缓存工具
     　　  * SpringBoot2.0以上CacheManager配置方式
     * @param redisTemplate
     * @return
     * */
    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
                // 设置value 为自动转Json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                // 不缓存null
                .disableCachingNullValues()
                // 缓存数据保存1小时
                .entryTtl(Duration.ofHours(1));
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                // Redis 连接工厂
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                // 缓存配置
                .cacheDefaults(defaultCacheConfiguration)
                // 配置同步修改或删除 put/evict
                .transactionAware()
                .build();

//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        return redisCacheManager;
    }


    /**
     * retemplate相关配置
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}