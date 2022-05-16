/**
 *
 */
package cn.px.base.support.context;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import cn.px.base.Constants;
import cn.px.base.util.DataUtil;
import cn.px.base.util.PropertiesUtil;
import cn.px.base.util.SecurityUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 *
 * @author PXHLT
 * @version 2018年12月1日 上午10:48:48
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer implements ApplicationContextAware {
	private List<String> decryptProperties;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.applicationContext = applicationContext;
	}

	@Override
	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		for (Entry<Object, Object> entry : props.entrySet()) {
			String keyStr = entry.getKey().toString();
			String value = entry.getValue().toString();
			if (decryptProperties != null && decryptProperties.contains(keyStr)) {
				String dkey = props.getProperty("druid.key");
				dkey = DataUtil.isEmpty(dkey) ? Constants.DB_KEY : dkey;
				value = SecurityUtil.decryptDes(value, dkey.getBytes());
				props.setProperty(keyStr, value);
			}
			PropertiesUtil.getProperties().put(keyStr, value);
		}
		logger.info("loadProperties ok.");
	}

	/**
	 * @param decryptProperties the decryptPropertiesMap to set
	 */
	public void setDecryptProperties(List<String> decryptProperties) {
		this.decryptProperties = decryptProperties;
	}
}
