package cn.px.sys.modular.mechanism.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.mechanism.entity.MechanismEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 机构管理(Mechanism)表数据库访问层
 *
 * @author
 * @since 2020-09-01 11:26:05
 */
public interface MechanismMapper extends BaseMapperImpl<MechanismEntity> {
    /**
     * 查询列表
     * @param page
     * @return
     */
    public Page<Map<String,Object>> getList(@Param("page") Page page);

}