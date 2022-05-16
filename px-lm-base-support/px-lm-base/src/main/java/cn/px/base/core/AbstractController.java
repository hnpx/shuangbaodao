/**
 *
 */
package cn.px.base.core;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import cn.px.base.support.Pagination;
import cn.px.base.support.context.ApplicationContextHolder;
import cn.px.base.support.http.HttpCode;
import cn.px.base.util.InstanceUtil;
import cn.px.base.util.ThreadUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;

/**
 * 控制器基类
 *
 * @author PXHLT
 * @version 2019年5月20日 下午3:47:58
 */
public abstract class AbstractController implements InitializingBean, ApplicationContextAware {
	protected Logger logger = LogManager.getLogger();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Field[] fields = getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object v = field.get(this);
				Class<?> cls = field.getType();
				if (v == null && cls.getSimpleName().toLowerCase().contains("service")) {
					v = ApplicationContextHolder.getService(cls);
					field.set(this, v);
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			logger.error("", e);
			ThreadUtil.sleep(1, 5);
			afterPropertiesSet();
		}
	}



	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap() {
		return setSuccessModelMap(new ModelMap(), null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
		return setSuccessModelMap(modelMap, null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(Object data) {
		return setModelMap(new ModelMap(), HttpCode.OK, data);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
		return setModelMap(modelMap, HttpCode.OK, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(HttpCode code) {
		return setModelMap(new ModelMap(), code, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(String code, String msg) {
		return setModelMap(new ModelMap(), code, msg, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
		return setModelMap(modelMap, code, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(HttpCode code, Object data) {
		return setModelMap(new ModelMap(), code, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(String code, String msg, Object data) {
		return setModelMap(new ModelMap(), code, msg, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
		return setModelMap(modelMap, code.value().toString(), code.msg(), data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, String code, String msg, Object data) {
		if (!modelMap.isEmpty()) {
			Map<String, Object> map = InstanceUtil.newLinkedHashMap();
			map.putAll(modelMap);
			modelMap.clear();
			for (Entry<String, Object> entry : map.entrySet()) {
				if (!entry.getKey().startsWith("org.springframework.validation.BindingResult")
						&& !"void".equals(entry.getKey())) {
					modelMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		if (data != null) {
			if (data instanceof Page<?>) {
				Page<?> page = (Page<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
			} else if (data instanceof Pagination<?>) {
				Pagination<?> page = (Pagination<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
			} else if (data instanceof List<?>) {
				modelMap.put("data", data);
				modelMap.put("total", ((List<?>) data).size());
			} else {
				modelMap.put("data", data);
			}
		}
		modelMap.put("httpCode", code);
		modelMap.put("msg", msg);
		modelMap.put("timestamp", System.currentTimeMillis());
		logger.info("response===>{}", JSON.toJSONString(modelMap));
		return ResponseEntity.ok(modelMap);
	}
}
