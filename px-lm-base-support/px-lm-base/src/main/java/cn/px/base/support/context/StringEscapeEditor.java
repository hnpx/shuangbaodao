package cn.px.base.support.context;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

/**
 * @author PXHLT
 * @since 2019年4月4日 下午3:00:44
 */
public class StringEscapeEditor extends PropertyEditorSupport {

    private boolean escapeHTML;// 编码HTML
    private boolean escapeJavaScript;// 编码javascript

    public StringEscapeEditor() {
        super();
    }

    public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                value = HtmlUtils.htmlEscape(value);
                System.out.println("value:"+value);
            }
            if (escapeJavaScript) {
                value = JavaScriptUtils.javaScriptEscape(value);
                System.out.println("value:"+value);
            }
            setValue(value);
        }
    }
}
