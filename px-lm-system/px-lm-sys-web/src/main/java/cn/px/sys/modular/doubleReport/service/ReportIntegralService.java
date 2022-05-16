package cn.px.sys.modular.doubleReport.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.doubleReport.entity.ReportIntegralEntity;
import cn.px.sys.modular.doubleReport.mapper.ReportIntegralMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ReportIntegral)表服务实现类
 *
 * @author
 * @since 2020-09-11 17:52:54
 */
@Service("reportIntegralService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "reportIntegral")
public class ReportIntegralService extends BaseServiceImpl<ReportIntegralEntity, ReportIntegralMapper> {

    /**
     * 查询列表
     * @return
     */
    public List<ReportIntegralEntity> getReportIntegralEntity(){
        return super.mapper.getReportIntegralEntity();
    }

}