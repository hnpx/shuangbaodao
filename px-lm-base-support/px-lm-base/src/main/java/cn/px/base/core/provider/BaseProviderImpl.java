package cn.px.base.core.provider;

import cn.px.base.Constants;
import cn.px.base.util.InstanceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;

/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:58:20
 */
public abstract class BaseProviderImpl implements ApplicationContextAware, BaseProvider {
    protected Logger logger = LogManager.getLogger();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Parameter execute(Parameter parameter) {
        String no = parameter.getNo();
        logger.info("{} request：{}", no, JSON.toJSONString(parameter));
        Object service = applicationContext.getBean(parameter.getService());
        try {
            String method = parameter.getMethod();
            Object[] param = parameter.getParam();
            Object result = InstanceUtil.invokeMethod(service, method, param);
            Parameter response = new Parameter(result);
            logger.info("{} response：{}", no, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            logger.error(no + " " + Constants.EXCEPTION_HEAD, e);
            throw e;
        }
    }

    @Override
    public Object execute(String service, String method, Object... parameters) {
        logger.info("{}.{} request：{}", service, method, JSON.toJSONString(parameters));
        Object owner = applicationContext.getBean(service);
        try {
            Object result = InstanceUtil.invokeMethod(owner, method, parameters);
            Parameter response = new Parameter(result);
            logger.info("{}.{} response：{}", service, method, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            logger.error(service + "." + method + " " + Constants.EXCEPTION_HEAD, e);
            throw e;
        }
    }
}
