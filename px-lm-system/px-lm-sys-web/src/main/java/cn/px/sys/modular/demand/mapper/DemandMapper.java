package cn.px.sys.modular.demand.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.demand.entity.DemandEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 需求管理(Demand)表数据库访问层
 *
 * @author
 * @since 2020-09-02 19:38:23
 */
public interface DemandMapper extends BaseMapperImpl<DemandEntity> {
    /**
     * 需求查询列表
     * @param page
     * @param name
     * @param status
     * @return
     */
    Page<Map<String,Object>> getListPage(@Param("page") Page page,@Param("name") String name,@Param("status") Integer status,@Param("cid") Long cid,@Param("phone") String phone,@Param("demandClass") Long demandClass);

}