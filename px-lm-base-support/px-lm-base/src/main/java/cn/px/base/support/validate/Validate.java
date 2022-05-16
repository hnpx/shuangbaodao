package cn.px.base.support.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Validate {
    /** 是否可以为空 */
    boolean nullable() default true;

    /** 最大值 */
    double max() default 0;

    /** 最小值 */
    double min() default 0;

    /** 最大长度 */
    int maxLength() default 0;

    /** 最小长度 */
    int minLength() default 0;

    /** 提供几种常用的正则验证 */
    RegexType type() default RegexType.NONE;

    /** 自定义正则验证 */
    String regex() default "";

    /** 参数或者字段描述 */
    String desc() default "";
}