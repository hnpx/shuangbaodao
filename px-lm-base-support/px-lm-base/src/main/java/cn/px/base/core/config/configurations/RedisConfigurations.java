package cn.px.base.core.config.configurations;

import java.util.Map;

import lombok.Data;

/**
 * Redis配置信息
 * 
 * @author PXHLT
 *
 */
@Data
public class RedisConfigurations {
	private String host;
	private Integer port;
	private String password;
	private Integer minIdle;
	private Integer maxIdle;
	private Integer maxTotal;
	private Integer maxWaitMillis;
	private Integer timeout;
	private Integer commandTimeout;
	private Integer shutdownTimeout;
	private String testOnBorrow;
	private Integer expiration;
	private Map<String, String> cache;
	private Map<String, String> cluster;
	private String enableTransaction;
	private int database;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Integer maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getCommandTimeout() {
		return commandTimeout;
	}

	public void setCommandTimeout(Integer commandTimeout) {
		this.commandTimeout = commandTimeout;
	}

	public Integer getShutdownTimeout() {
		return shutdownTimeout;
	}

	public void setShutdownTimeout(Integer shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Integer getExpiration() {
		return expiration;
	}

	public void setExpiration(Integer expiration) {
		this.expiration = expiration;
	}

	public Map<String, String> getCache() {
		return cache;
	}

	public void setCache(Map<String, String> cache) {
		this.cache = cache;
	}

	public Map<String, String> getCluster() {
		return cluster;
	}

	public void setCluster(Map<String, String> cluster) {
		this.cluster = cluster;
	}

	public String getEnableTransaction() {
		return enableTransaction;
	}

	public void setEnableTransaction(String enableTransaction) {
		this.enableTransaction = enableTransaction;
	}

}
