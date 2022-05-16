/**
 * 
 */
package cn.px.base.support.dbcp.provider;

import cn.px.base.core.provider.Parameter;
import cn.px.base.support.dbcp.HandleDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * 
 * @author PXHLT
 * @version 2018年9月19日 上午11:44:20
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {
    static {
        System.setProperty("druid.logType", "log4j2");
    }

    @Pointcut("this(com.jy.core.base.provider.BaseProviderImpl)")
    public void aspect() {
    }

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     */
    @Before("aspect()")
    public void before(JoinPoint point) {
        Parameter parameter = (Parameter)point.getArgs()[0];
        String service = parameter.getService();
        String method = parameter.getMethod();
        HandleDataSource.setDataSource(service, method);
    }

    @After("aspect()")
    public void after(JoinPoint point) {
        HandleDataSource.clear();
    }
}
