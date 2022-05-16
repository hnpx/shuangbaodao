package cn.px.base.i18n.dict;

import cn.px.base.i18n.enums.TranslationEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 多语言表
 * </p>
 *
 * @author PXHLT
 * @since 2019-10-17
 */
@Data
public class TranslationDict implements Serializable {

    /**
     * 编码
     */
    private String tranCode;

    /**
     * 多语言条例名称
     */
    private String tranName;

    /**
     * 1:中文  2:英语
     */
    private TranslationEnum translationLanguages;

    /**
     * 翻译的值
     */
    private String tranValue;

}
