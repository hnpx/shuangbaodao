package cn.px.sys.modular.activity.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.activity.entity.ActivityPlanEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * (ActivityPlan)表数据库访问层
 *
 * @author
 * @since 2020-09-28 09:16:43
 */
public interface ActivityPlanMapper extends BaseMapperImpl<ActivityPlanEntity> {

    Page<Map<String,Object>> getlist(@Param("page") Page page, @Param("uid") Long id);


}