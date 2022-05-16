package cn.px.sys.modular.expertise.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.expertise.entity.ExpertiseClassEntity;
import cn.px.sys.modular.expertise.mapper.ExpertiseClassMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * (ExpertiseClass)表服务实现类
 *
 * @author
 * @since 2020-10-10 11:21:35
 */
@Service("expertiseClassService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "expertiseClass")
public class ExpertiseClassService extends BaseServiceImpl<ExpertiseClassEntity, ExpertiseClassMapper> {

}