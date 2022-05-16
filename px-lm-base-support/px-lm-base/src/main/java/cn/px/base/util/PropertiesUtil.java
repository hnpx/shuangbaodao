package cn.px.base.util;

import cn.px.base.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import java.util.MissingResourceException;
import java.util.Properties;

/**
 * Parsing The Configuration File
 *
 * @author PXHLT
 * @version 2016年7月30日 下午11:41:53
 */
public final class PropertiesUtil {
	private static Map<String, String> ctxPropertiesMap = new HashMap<String, String>();

	public static Map<String, String> getProperties() {
		return ctxPropertiesMap;
	}

	/**
	 * Get a value based on key , if key does not exist , null is returned
	 *
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return ctxPropertiesMap.get(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * Get a value based on key , if key does not exist , null is returned
	 *
	 * @param key
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		try {
			String value = ctxPropertiesMap.get(key);
			if (DataUtil.isEmpty(value)) {
				return defaultValue;
			}
			return value;
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}

	/**
	 * 根据key获取值
	 *
	 * @param key
	 * @return
	 */
	public static Integer getInt(String key) {
		String value = ctxPropertiesMap.get(key);
		if (DataUtil.isEmpty(value)) {
			return null;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 根据key获取值
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		String value = ctxPropertiesMap.get(key);
		if (DataUtil.isEmpty(value)) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 从配置文件中取得 long 值，若无（或解析异常）则返回默认值
	 * 
	 * @param keyName      属性名
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public static long getLong(String keyName, long defaultValue) {
		String value = getString(keyName);
		if (DataUtil.isEmpty(value)) {
			return defaultValue;
		}
		try {
			return Long.parseLong(value.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static boolean getBoolean(String key) {
		String value = ctxPropertiesMap.get(key);
		if (DataUtil.isEmpty(value)) {
			return false;
		}
		return Boolean.parseBoolean(value.trim());
	}

	/**
	 * 根据key获取值
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = ctxPropertiesMap.get(key);
		if (DataUtil.isEmpty(value)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value.trim());
	}

	/**
	 * 初始化配置文件
	 * 
	 * @param props
	 */
	public static void init(Properties props) {
		for (Entry<Object, Object> entry : props.entrySet()) {
			String keyStr = entry.getKey().toString();
			String value = entry.getValue().toString();
			if ("druid.reader.password".equals(keyStr) || "druid.writer.password".equals(keyStr)) {
				String dkey = Constants.DB_KEY;
				value = SecurityUtil.decryptDes(value, dkey.getBytes());
				props.setProperty(keyStr, value);
			}
			PropertiesUtil.getProperties().put(keyStr, value);
		}
	}

	public static void main(String[] args) {
		String pass = "PXHLT";
		String dkey = Constants.DB_KEY;
		pass = SecurityUtil.encryptDes(pass, dkey.getBytes());
		System.out.println(pass);
	}
}
