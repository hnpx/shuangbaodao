package cn.px.base.core.config;

import cn.px.base.core.config.configurations.RedisConfigurations;
import cn.px.base.support.cache.RedissonHelper;
import cn.px.base.support.redisson.Client;
import cn.px.base.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Redis连接配置
 *
 * @author PXHLT
 * @since 2017年8月14日 上午10:17:29
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
public class RedissonConfig {
	@Autowired
	private RedisConfigurations redisConfig;
	@Bean
	public RedissonClient redissonClient(RedisConfigurations redisConfig) {
		Client client = new Client();
		String nodes = redisConfig.getCluster().get("nodes");
		String master = "";
		String sentinels = "";
		if (StringUtils.isNotBlank(nodes)) {
			client.setNodeAddresses(nodes);
		} else if (DataUtil.isNotEmpty(master) && DataUtil.isNotEmpty(sentinels)) {
			client.setMasterAddress(master);
			client.setSlaveAddresses(sentinels);
		} else {
			String host = redisConfig.getHost();
			String address = "redis://" + host + ":" + redisConfig.getPort();
			client.setAddress(address);
		}

		client.setPassword(this.redisConfig.getPassword());
		client.setTimeout(redisConfig.getTimeout());
		RedissonClient rc = client.getRedissonClient();
		return rc;
	}

	@Bean
	public RedissonHelper redissonHelper(RedissonClient client) {
		RedissonHelper helper = new RedissonHelper();
		helper.setRedissonClient(client);
		return helper;
	}
}
