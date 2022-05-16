package cn.px.sys.modular.demandClass.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.demandClass.entity.DemandClassEntity;
import cn.px.sys.modular.demandClass.mapper.DemandClassMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * 需求分类(DemandClass)表服务实现类
 *
 * @author
 * @since 2020-08-27 15:45:35
 */
@Service("demandClassService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "demandClass")
public class DemandClassService extends BaseServiceImpl<DemandClassEntity, DemandClassMapper> {

}