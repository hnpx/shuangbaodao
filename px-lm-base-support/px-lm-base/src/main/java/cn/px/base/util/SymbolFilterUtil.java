package cn.px.base.util;

import cn.hutool.extra.emoji.EmojiUtil;
import cn.px.base.core.annotation.SymbolFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SymbolFilterUtil {
    private static Logger logger = LogManager.getLogger(SymbolFilterUtil.class);
    private static final Map<String, Set<Field>> cacheMap = new ConcurrentHashMap<>();

    public static void defaultSymbolFilter(Object obj){
        symbolFilter(obj, field -> SymbolFilterUtil.doFilter(field));
    }

    public static void symbolFilter(Object obj, Function<Object, Object> handler){
        Class<?> objClass = obj.getClass();
        if(cacheMap.get(objClass.getName())==null){
            initObj(objClass);
        }
        Set<Field> fields = cacheMap.get(objClass.getName());
        if(fields.size()==0)
            return;
        fields.stream().forEach(field -> {
            try {
                Object vaule = field.get(obj);
                vaule = handler.apply(vaule);
                field.set(obj, vaule);
            } catch (IllegalAccessException e) {
               // e.printStackTrace();
                logger.error(objClass.getName()+"::"+field.getName()+"通过反射设置值失败");
            }
        });
    }

    private static void initObj(Class clazz){
        if(cacheMap.get(clazz.getName())==null){
            synchronized (clazz){
                if(cacheMap.get(clazz.getName())==null){
                    HashSet<Field> set = new LinkedHashSet<>();
                    ReflectionUtils.doWithFields(clazz, field -> {
                        if(field.getAnnotation(SymbolFilter.class)!=null){
                            field.setAccessible(true);
                            set.add(field);
                        }
                    });
                    cacheMap.put(clazz.getName(), Collections.unmodifiableSet(set));
                }
            }
        }
    }

    public static Object doFilter(Object obj) {
        if (obj == null)
            return obj;
        if (!(obj instanceof String))
            return obj;
        String str = (String) obj;
        str = EmojiUtil.removeAllEmojis(str);

        str = str.replaceAll("[^A-Za-z0-9 \\u4e00-\\u9fa5]", "");
        return str;
    }
}
