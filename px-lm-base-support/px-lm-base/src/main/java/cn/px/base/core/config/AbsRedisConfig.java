package cn.px.base.core.config;

import cn.px.base.core.config.configurations.RedisConfigurations;
import cn.px.base.support.cache.RedisHelper;
import cn.px.base.util.DataUtil;
import cn.px.base.util.InstanceUtil;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:58:48
 */
public abstract class AbsRedisConfig {
	public abstract RedisConfigurations redisConfigurations();

	@Bean
	public GenericObjectPoolConfig redisPoolConfig(RedisConfigurations redisConfig) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(redisConfig.getMinIdle());
		config.setMaxIdle(redisConfig.getMaxIdle());
		config.setMaxTotal(redisConfig.getMaxTotal());
		config.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		// Idle时进行连接扫描
		config.setTestWhileIdle(true);
		// 表示idle object evitor两次扫描之间要sleep的毫秒数
		config.setTimeBetweenEvictionRunsMillis(30000);
		// 表示idle object evitor每次扫描的最多的对象数
		config.setNumTestsPerEvictionRun(10);
		// 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐
		// 这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		config.setMinEvictableIdleTimeMillis(60000);
		return config;
	}

	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean(ClientResources.class)
	public ClientResources clientResources() {
		return DefaultClientResources.create();
	}

	@Bean
	@ConditionalOnMissingBean(RedisConnectionFactory.class)
	public RedisConnectionFactory redisConnectionFactory(GenericObjectPoolConfig redisPoolConfig,
														 ClientResources clientResources, RedisConfigurations redisConfig) {
		LettuceConnectionFactory connectionFactory;
		String nodes = redisConfig.getCluster().get("nodes");
		String master = "";
		String sentinels = "";
		Duration commandTimeout = Duration.ofMillis(redisConfig.getCommandTimeout());
		Duration shutdownTimeout = Duration.ofMillis(redisConfig.getShutdownTimeout());
		LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder()
				.poolConfig(redisPoolConfig).commandTimeout(commandTimeout).shutdownTimeout(shutdownTimeout)
				.clientResources(clientResources);
		LettuceClientConfiguration clientConfiguration = builder.build();
		RedisPassword password = RedisPassword.of(redisConfig.getPassword());
		String host = redisConfig.getHost();
		Integer port = (redisConfig.getPort());
		if (DataUtil.isNotEmpty(nodes)) {
			List<String> list = InstanceUtil.newArrayList(nodes.split(","));
			RedisClusterConfiguration configuration = new RedisClusterConfiguration(list);
			configuration.setMaxRedirects(Integer.parseInt(redisConfig.getCluster().get("max-redirects")));
			configuration.setPassword(password);
			connectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
		} else if (DataUtil.isNotEmpty(master) && DataUtil.isNotEmpty(sentinels)) {
			Set<String> set = InstanceUtil.newHashSet(sentinels.split(","));
			RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(master, set);
			configuration.setPassword(password);
			connectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
		} else {
			RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setPassword(password);
			configuration.setHostName(host);
			configuration.setPort(port);
			connectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
		}
		connectionFactory.setDatabase(redisConfig.getDatabase());
		return connectionFactory;
	}

	@Bean
	public RedisTemplate<Serializable, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory,
																   RedisConfigurations redisConfig) {
		RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
		StringRedisSerializer keySerializer = new StringRedisSerializer();
		GenericFastJsonRedisSerializer valueSerializer = new GenericFastJsonRedisSerializer();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setValueSerializer(valueSerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		redisTemplate.setHashValueSerializer(valueSerializer);
		redisTemplate.setEnableTransactionSupport(new Boolean(redisConfig.getEnableTransaction()));
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(CacheManager.class)
	public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
										  RedisConfigurations redisConfig) {
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(Integer.parseInt(redisConfig.getCache().get("ttl"))));
		RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(configuration);
		if (new Boolean(redisConfig.getCache().get("enableTransaction"))) {
			builder.transactionAware();
		}
		RedisCacheManager cacheManager = builder.build();
		return cacheManager;
	}

	@Bean
	public RedisHelper redisHelper(RedisConnectionFactory redisConnectionFactory, RedisConfigurations redisConfig) {
		return redisHelper(redisTemplate(redisConnectionFactory, redisConfig), redisConfig);
	}

	public RedisHelper redisHelper(RedisTemplate<Serializable, Serializable> redisTemplate,
								   RedisConfigurations redisConfig) {
		return new RedisHelper(redisTemplate, redisConfig.getExpiration());
	}
}
