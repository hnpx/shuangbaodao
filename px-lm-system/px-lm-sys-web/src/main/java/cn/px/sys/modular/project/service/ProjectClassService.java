package cn.px.sys.modular.project.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.project.entity.ProjectClassEntity;
import cn.px.sys.modular.project.mapper.ProjectClassMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * (ProjectClass)表服务实现类
 *
 * @author
 * @since 2020-09-02 20:19:31
 */
@Service("projectClassService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "projectClass")
public class ProjectClassService extends BaseServiceImpl<ProjectClassEntity, ProjectClassMapper> {

}