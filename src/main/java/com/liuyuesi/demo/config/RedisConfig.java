package com.liuyuesi.demo.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.liuyuesi.demo.entity.User;

@Configuration
//@EnableRedisRepositories
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

	//默认两天的过期时间
	private final int defaultExpireTime = 2;
	
	//默认起始存储空间变量为AccessToken String
	private final String defaultCacheName = "AccessToken";
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}
	 
	//设置自定义缓存
	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
	
	@Bean
	public RedisTemplate<String,Integer> accessTokenRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String,Integer> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
		
	}
	//自动生成一个10000以内的随机key
	@Bean
	public KeyGenerator accessTokenGenerator() {
		return new KeyGenerator() {
			public Object generate(Object o, Method method, Object... params) {
				//随机获取10000以内的一个数字
				Integer accessToken = new Random().nextInt(10000);
				return accessToken.toString();			
			}	
		};
	}
	
	//设置以Redis Cache为Spring Cache的函数
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration= redisCacheConfiguration
				//设置key从string到byte的双向转化 转化配置为StringRedisSerializer
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))
				//设置value从object到JSON的双向转化序列化 转化配置为GenericJackson2JsonRedisSerializer
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				//不缓存null
				.disableCachingNullValues();
				//设置每个缓存对象的缓存时间 时间为2天
				//.entryTtl(Duration.ofDays(defaultExpireTime));
		
		//设置初始缓存空间名字 Set保证缓存空间唯一性
		Set<String> cacheNames = new HashSet<>();
		//将Access Token作为起始默认的第一个缓存空间 存入缓存空间set
		cacheNames.add(defaultCacheName);
		
		//将每个缓存空间与设定好的redisCacheConfiguration匹配 具体匹配过期时间
		Map<String,RedisCacheConfiguration> configMap = new HashMap<>();
		configMap.put(defaultCacheName, redisCacheConfiguration.entryTtl(Duration.ofDays(defaultExpireTime)));
		
		RedisCacheManager cacheManager = RedisCacheManager
				//redisConnection用传进来的redisConnection
				.builder(redisConnectionFactory())
				//定义Cache规则 其中包含序列化规则和不允许null存储
				.cacheDefaults(redisCacheConfiguration)
				//设定缓存空间名字
				.initialCacheNames(cacheNames)
				//设定每个缓存空间的规则
				.withInitialCacheConfigurations(configMap)
				//Create new instance of RedisCacheManager with configuration options applied.
				.build();
		
		return cacheManager;			
	}
}
