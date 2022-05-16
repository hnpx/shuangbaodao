package cn.px.sys.modular.system.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.system.entity.SysFaqEntity;
import cn.px.sys.modular.system.mapper.SysFaqMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * (SysFaq)表服务实现类
 *
 * @author makejava
 * @since 2020-06-09 14:43:58
 */
@Service("sysFaqService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"sysFaq")
public class SysFaqService extends BaseServiceImpl<SysFaqEntity,SysFaqMapper> {

}
