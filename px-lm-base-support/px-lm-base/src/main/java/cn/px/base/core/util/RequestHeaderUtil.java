package cn.px.base.core.util;

import javax.servlet.http.HttpServletRequest;

public class RequestHeaderUtil {

    /**
     * 获取head中的值
     * @param request
     * @param key
     * @return
     */
    public static Long getHeaderToLong(HttpServletRequest request,String key){
        String headVal=getHeader(request,key);
        if(headVal!=null) {
            return Long.parseLong(headVal);
        }else{
            return null;
        }
    }

    /**
     * 获取request中的值
     * @param request
     * @param key
     * @return
     */
    public static String getHeader(HttpServletRequest request,String key){
        String headVal=request.getHeader(key);
        return headVal;
    }
}
