package cn.px.sys.modular.doubleReport.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.doubleReport.entity.ReportIntegralEntity;

import java.util.List;

/**
 * (ReportIntegral)表数据库访问层
 *
 * @author
 * @since 2020-09-11 17:53:08
 */
public interface ReportIntegralMapper extends BaseMapperImpl<ReportIntegralEntity> {
    /**
     * 查询列表
     * @return
     */
    public List<ReportIntegralEntity>  getReportIntegralEntity();


}