package cn.px.base.core.annotation;

import java.lang.annotation.*;

/**
 * 添加该注解的字段的值在插入和更新时会过滤特殊字符
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SymbolFilter {

}
