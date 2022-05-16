/**
 * 
 */
package cn.px.base.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * 
 * @author PXHLT
 * @version 2019年1月5日 上午9:46:04
 */
@SuppressWarnings("rawtypes")
public class ApplicationReadyListener implements ApplicationListener {
	protected final Logger logger = LogManager.getLogger();

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量
			logger.info("==========初始化环境变量==============");
			ApplicationEnvironmentPreparedEvent aevent = (ApplicationEnvironmentPreparedEvent) event;
			// 加载配置文件
			ConfigurableEnvironment env = aevent.getEnvironment();
			// 加载配置文件
			if (env.getActiveProfiles().length == 0) {
				env.setActiveProfiles("dev");
			}
			logger.info("==========加载配置信息完成==============");
		} else if (event instanceof ApplicationPreparedEvent) { // 初始化完成
			logger.info("==========初始化完成==============");
		} else if (event instanceof ContextRefreshedEvent) { // 应用刷新
			logger.info("==========应用刷新==============");
		} else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成
			logger.info("=================================");
			String server = ((ApplicationReadyEvent) event).getSpringApplication().getAllSources().iterator().next()
					.toString();
			logger.info("系统[{}]启动完成!!!", server.substring(server.lastIndexOf(".") + 1));
			logger.info("=================================");
		} else if (event instanceof ContextStartedEvent) { // 应用启动，需要在代码动态添加监听器才可捕获
			logger.info("==========应用启动==============");
		} else if (event instanceof ContextStoppedEvent) { // 应用停止
			logger.info("==========应用停止==============");
		} else if (event instanceof ContextClosedEvent) { // 应用关闭
			logger.info("==========应用关闭==============");
		} else {
		}
	}
}
