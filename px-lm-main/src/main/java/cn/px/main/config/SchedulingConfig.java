package cn.px.main.config;

import cn.px.main.core.schedue.quartz.StartQuartzExample;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务自动配置(需要定时任务的可以放开注释)
 *
 * @author fengshuonan
 * @Date 2019/2/24 16:23
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    /**
     * quartz方式，配置Scheduler实例
     *
     * @author fengshuonan
     * @Date 2019/2/24 19:03
     */
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }

    /**
     * 启动quartz的示例
     *
     * @author fengshuonan
     * @Date 2019/3/27 3:34 PM
     */
   // @Bean
    public StartQuartzExample startQuartzExample() {
        return new StartQuartzExample();
    }

}
