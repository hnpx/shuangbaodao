package cn.px.sys.modular.system.service;

import cn.px.sys.modular.system.entity.SysConfigHotWordEntity;
import cn.px.sys.modular.system.mapper.SysConfigHotWordMapper;
import org.springframework.stereotype.Service;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.Constants;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 热词(SysConfigHotWord)表服务实现类
 *
 * @author makejava
 * @since 2020-07-22 21:50:49
 */
@Service("sysConfigHotWordService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"sysConfigHotWord")
public class SysConfigHotWordService extends BaseServiceImpl<SysConfigHotWordEntity,SysConfigHotWordMapper> {
   
}