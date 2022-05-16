package cn.px.base.core;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.px.base.util.InstanceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;


/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:57:33
 * @param <T>
 * @param <S>
 */
public abstract class AppBaseController<T extends BaseModel, S extends BaseService<T>> extends BaseController<T, S> {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    public Object update(HttpServletRequest request, T param,Long currentUserId) {
        return update(request, new ModelMap(), param,currentUserId);
    }

    public Object update(HttpServletRequest request, ModelMap modelMap, T param,Long currentUserId) {
        if (param.getId() == null) {
            param.setCreateBy(currentUserId);
            param.setCreateTime(new Date());
        }
        param.setUpdateBy(currentUserId);
        param.setUpdateTime(new Date());
        Object record = service.update(param);
        Map<String, Object> result = InstanceUtil.newHashMap("bizeCode", 1);
        result.put("record", record);
        return setSuccessModelMap(modelMap, result);
    }
}
