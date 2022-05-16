package cn.px.main.config.redis;

		import cn.px.base.core.config.AbsRedisConfig;
		import cn.px.base.core.config.configurations.RedisConfigurations;
		import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
		import org.springframework.boot.context.properties.ConfigurationProperties;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.data.redis.cache.RedisCacheConfiguration;


/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:58:48
 */
@Configuration
@ConditionalOnClass(RedisCacheConfiguration.class)
public class RedisConfig extends AbsRedisConfig {
	@Override
	@Bean
	@ConfigurationProperties("redis")
	public RedisConfigurations redisConfigurations() {
		return new RedisConfigurations();
	}
}
