package cn.px.sys.modular.activity.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.activity.entity.ActivityPlanEntity;
import cn.px.sys.modular.activity.mapper.ActivityPlanMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * (ActivityPlan)表服务实现类
 *
 * @author
 * @since 2020-09-28 09:16:30
 */
@Service("activityPlanService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "activityPlan")
public class ActivityPlanService extends BaseServiceImpl<ActivityPlanEntity, ActivityPlanMapper> {

    public  Page<Map<String,Object>> getlist(Page page, Long id){
        return mapper.getlist(page,id);
    }


}