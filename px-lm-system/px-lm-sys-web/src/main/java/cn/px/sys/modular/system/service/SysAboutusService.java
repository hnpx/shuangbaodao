package cn.px.sys.modular.system.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.system.entity.SysAboutusEntity;
import cn.px.sys.modular.system.mapper.SysAboutusMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * (SysAboutus)表服务实现类
 *
 * @author makejava
 * @since 2020-06-09 14:43:39
 */
@Service("sysAboutusService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"sysAboutus")
public class SysAboutusService extends BaseServiceImpl<SysAboutusEntity,SysAboutusMapper> {

}
