/**
 *
 */
package cn.px.base.core;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.support.context.ApplicationContextHolder;
import cn.px.base.support.http.SessionUser;
import cn.px.base.util.ThreadUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;


/**
 * 控制器基类
 *
 * @author PXHLT
 * @version 2019年5月20日 下午3:47:58
 */
public abstract class BaseController<T extends BaseModel, S extends BaseService<T>> extends AbstractController
        implements InitializingBean {
    protected S service;

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        if (service == null) {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            Class<?> cls = (Class<?>) type.getActualTypeArguments()[1];
            try {
                service = (S) ApplicationContextHolder.getService(cls);
            } catch (Exception e) {
                logger.error("", e);
                ThreadUtil.sleep(1, 5);
                afterPropertiesSet();
            }
        }
        super.afterPropertiesSet();
    }

    /** 分页查询 */
    public Object query(ModelMap modelMap, T param, Page<Long> page) {
        Map<String, Object> paramMap = BeanUtil.beanToMap(param);
        Object pageObject = service.query(paramMap, page);
        return setSuccessModelMap(modelMap, pageObject);
    }

    /** 分页查询 */
    public Object query(ModelMap modelMap, Map<String, Object> param, Page<Long> page) {
        Map<String, Object> paramMap = BeanUtil.beanToMap(param);
        Object pageObject = service.query(paramMap, page);
        return setSuccessModelMap(modelMap, pageObject);
    }

    /** 分页查询 */
    public Object query(Map<String, Object> param) {
        return query(new ModelMap(), param);
    }

    public Object query(ModelMap modelMap, T param, Integer pageNum, Integer pageSize) {
        Map<String, Object> paramMap = BeanUtil.beanToMap(param);
        paramMap.put("pageSize", pageSize);
        paramMap.put("pageNum", pageNum);
        Object page = service.query(paramMap);
        return setSuccessModelMap(modelMap, page);
    }

    /** 分页查询 */
    public Object query(ModelMap modelMap, Map<String, Object> param) {
        if (param.get("keyword") == null && param.get("search") != null) {
            param.put("keyword", param.get("search"));
            param.remove("search");
        }
        Object page = service.query(param);
        return setSuccessModelMap(modelMap, page);
    }

    /** 查询 */
    public Object queryList(Map<String, Object> param) {
        return queryList(new ModelMap(), param);
    }

    /** 查询 */
    public Object queryList(ModelMap modelMap, Map<String, Object> param) {
        List<?> list = service.queryList(param);
        return setSuccessModelMap(modelMap, list);
    }

    public Object get(BaseModel param) {
        return get(new ModelMap(), param);
    }

    public Object get(ModelMap modelMap, BaseModel param) {
        Object result = service.queryById(param.getId());
        return setSuccessModelMap(modelMap, result);
    }

    public Object update(T param, Long currentUserId) {
        return update(new ModelMap(), param, currentUserId);
    }

    public Object update(ModelMap modelMap, T param, Long currentUserId) {
        Long userId = currentUserId;
        if (param.getId() == null) {
            param.setCreateBy(userId);
            param.setCreateTime(new Date());
            if (param.getEnable() == null) {
                param.setEnable(Constants.ENABLE_TRUE);// 增加的时候默认是可用状态
            }
        }
        param.setUpdateBy(userId);
        param.setUpdateTime(new Date());
        T result = service.update(param);
        param.setId(result.getId());
        return setSuccessModelMap(modelMap);
    }

    /** 物理删除 */
    public Object delete(T param) {
        return delete(new ModelMap(), param);
    }

    /** 物理删除 */
    public Object delete(ModelMap modelMap, T param) {
        Assert.notNull(param.getId(), "ID不能为空");
        service.delete(param.getId());
        return setSuccessModelMap(modelMap);
    }

    /** 逻辑删除 */
    public Object del(T param, Long currentUserId) {
        return del(new ModelMap(), param, currentUserId);
    }

    /** 逻辑删除 */
    public Object del(ModelMap modelMap, T param, Long currentUserId) {
        Long userId = currentUserId;
        if (param.getId() != null) {
            Assert.notNull(param.getId(), "ID不能为空");
            service.del(param.getId(), userId);
        }
        if (param.getIds() != null) {
            Assert.notNull(param.getIds(), "ID不能为空");
            service.del(param.getIds(), userId);
        }
        return setSuccessModelMap(modelMap);
    }
}
