package cn.px.base.support.cache;

import java.io.Serializable;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:59:43
 */
public interface CacheManager {
    Logger logger = LogManager.getLogger();

    Object get(final String key);

    Set<Object> getAll(final String pattern);

    void set(final String key, final Serializable value, int seconds);

    void set(final String key, final Serializable value);

    Boolean exists(final String key);

    void del(final String key);

    void delAll(final String pattern);

    String type(final String key);

    Boolean expire(final String key, final int seconds);

    Boolean expireAt(final String key, final long unixTime);

    Long ttl(final String key);

    Object getSet(final String key, final Serializable value);

    boolean lock(String key, String requestId, long seconds);

    boolean unlock(String key, String requestId);

    void hset(String key, Serializable field, Serializable value);

    Object hget(String key, Serializable field);

    void hdel(String key, Serializable field);

    boolean setnx(String key, Serializable value);

    Long incr(String key);

    Long incr(String key, int seconds);

    void setrange(String key, long offset, String value);

    String getrange(String key, long startOffset, long endOffset);

    Object get(String key, Integer expire);

    Object getFire(String key);

    Set<Object> getAll(String pattern, Integer expire);
}
