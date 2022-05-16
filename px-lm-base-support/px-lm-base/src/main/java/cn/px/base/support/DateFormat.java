package cn.px.base.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.px.base.util.DateUtil;

/**
 * 时间格式化扩展
 *
 * @author PXHLT
 * @since 2018年6月30日 下午7:40:00
 */
@SuppressWarnings("serial")
public class DateFormat extends SimpleDateFormat {

    public DateFormat(String pattern) {
        super(pattern);
    }

    @Override
    public Date parse(String source) throws ParseException {
        return DateUtil.stringToDate(source);
    }
}
