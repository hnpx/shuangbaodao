package cn.px;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.stylefeng.roses.core.config.MybatisDataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot方式启动类
 *
 * @author PXHLT
 */
@SpringBootApplication(exclude = {MybatisDataSourceAutoConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)
//@EnableCaching()
@EnableScheduling
public class LongMenApplication {

    private final static Logger logger = LoggerFactory.getLogger(LongMenApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LongMenApplication.class, args);
        logger.info(LongMenApplication.class.getSimpleName() + " is success!");
    }

}
