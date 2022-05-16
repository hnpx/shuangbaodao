package cn.px.base.core.config.configurations;

import java.util.Map;

import lombok.Data;

/**
 * 数据库连接池
 * 
 * @author PXHLT
 *
 */
@Data
public class DruidConfiguration {
	private Map<String, String> reader;
	private Map<String, String> writer;
	private Map<String, String> datasource;
	private String key;
	private String driverClassName;
	private String initialSize;
	private String maxActive;

}
