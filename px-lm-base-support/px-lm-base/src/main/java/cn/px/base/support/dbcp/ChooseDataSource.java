package cn.px.base.support.dbcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 获取数据源
 *
 * @author PXHLT
 * @version 2019年5月20日 下午3:17:16
 */
public class ChooseDataSource extends AbstractRoutingDataSource {

    // 获取数据源名称
    @Override
    protected Object determineCurrentLookupKey() {
        return HandleDataSource.getDataSource();
    }

    // 设置方法名前缀对应的数据源
    public void setMethodType(Map<String, String> map) {
        for (Entry<String, String> entry : map.entrySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = entry.getValue().split(",");
            for (String type : types) {
                if (StringUtils.isNotBlank(type)) {
                    v.add(type);
                }
            }
            HandleDataSource.METHODTYPE.put(entry.getKey(), v);
        }
    }
}
