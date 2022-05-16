package cn.px.base.support.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import cn.px.base.Constants;
import cn.px.base.util.DataUtil;
import cn.px.base.util.MathUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisHash;


/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:59:34
 */
public class CacheKey {
	private String value;
	private int timeToLive;

	public CacheKey(String value, int timeToLive) {
		this.value = value;
		this.timeToLive = timeToLive;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public static CacheKey getInstance(Class<?> cls, Integer expiration) {
		CacheKey cackKey = Constants.CACHEKEYMAP.get(cls);
		expiration = expiration == null ? 600 : expiration;// 默认是600
		if (DataUtil.isEmpty(cackKey)) {
			String cacheName;
			RedisHash redisHash = null;
			Long timeToLive = MathUtil.getRandom(1, 1.5).multiply(new BigDecimal(expiration)).longValue();
			ParameterizedType parameterizedType = (ParameterizedType) cls.getGenericSuperclass();
			if (parameterizedType != null) {
				Type[] actualTypes = parameterizedType.getActualTypeArguments();
				if (actualTypes != null && actualTypes.length > 0) {
					// 实体注解@RedisHash
					redisHash = actualTypes[0].getClass().getAnnotation(RedisHash.class);
				}
			}
			if (redisHash != null) {
				cacheName = redisHash.value();
				timeToLive = redisHash.timeToLive();
			} else {
				// Service注解CacheConfig
				CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
				if (cacheConfig != null && ArrayUtils.isNotEmpty(cacheConfig.cacheNames())) {
					cacheName = cacheConfig.cacheNames()[0];
				} else {
					return null;
				}
			}
			cackKey = new CacheKey(Constants.CACHE_NAMESPACE + cacheName, timeToLive.intValue());
			Constants.CACHEKEYMAP.put(cls, cackKey);
		}
		return cackKey;
	}

	public static CacheKey getInstance(Class<?> cls) {
		return getInstance(cls, 600);
	}
}
