package cn.px.base.support.http;

import java.util.List;
import java.util.Map;

import cn.px.base.support.Pagination;
import cn.px.base.util.DataUtil;
import cn.px.base.util.InstanceUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * @author PXHLT
 * @since 2019年8月29日 下午2:05:34
 */
public class ReturnValueHandler implements HandlerMethodReturnValueHandler {
    private HandlerMethodReturnValueHandler handler;

    public ReturnValueHandler(HandlerMethodReturnValueHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return handler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest) throws Exception {
        if (returnValue != null) {
            if (returnValue instanceof ResponseEntity) {
                handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            } else {
                Map<String, Object> modelMap = InstanceUtil.newHashMap();
                if (returnValue instanceof Pagination<?>) {
                    Pagination<?> page = (Pagination<?>)returnValue;
                    modelMap.put("rows", page.getRecords());
                    modelMap.put("current", page.getCurrent());
                    modelMap.put("size", page.getSize());
                    modelMap.put("pages", page.getPages());
                    modelMap.put("total", page.getTotal());
                } else if (returnValue instanceof List<?>) {
                    modelMap.put("rows", returnValue);
                    modelMap.put("total", ((List<?>)returnValue).size());
                } else if (DataUtil.isNotEmpty(returnValue)) {
                    modelMap.put("data", returnValue);
                }
                modelMap.put("code", 200);
                modelMap.put("msg", "");
                modelMap.put("timestamp", System.currentTimeMillis());
                handler.handleReturnValue(modelMap, returnType, mavContainer, webRequest);
            }
        } else {
            handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }
    }
}
