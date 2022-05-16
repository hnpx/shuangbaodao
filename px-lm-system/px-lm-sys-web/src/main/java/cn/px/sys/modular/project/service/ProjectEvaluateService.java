package cn.px.sys.modular.project.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import cn.px.sys.modular.project.mapper.ProjectEvaluateMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * (ProjectEvaluate)表服务实现类
 *
 * @author
 * @since 2020-09-28 11:03:23
 */
@Service("projectEvaluateService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "projectEvaluate")
public class ProjectEvaluateService extends BaseServiceImpl<ProjectEvaluateEntity, ProjectEvaluateMapper> {




    public Page<Map<String,Object>> getHomeList(@Param("pid") Long pid){
        Page page = LayuiPageFactory.defaultPage();
        return super.mapper.getHomeList(page,pid);
    }

    public int getCount(Long pid,Long userId){
        return super.mapper.getCount(pid,userId);
    }

    public ProjectEvaluateEntity getByPid(Long pid, Long userId){
        return super.mapper.getByPid(pid,userId);
    }


    public List<ProjectEvaluateEntity> getListByUser( Long userId){
     return super.mapper.getListByUser(userId);
    }

}