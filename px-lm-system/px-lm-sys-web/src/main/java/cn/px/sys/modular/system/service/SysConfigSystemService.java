package cn.px.sys.modular.system.service;

import cn.px.sys.modular.system.entity.SysConfigSystemEntity;
import cn.px.sys.modular.system.mapper.SysConfigSystemMapper;
import org.springframework.stereotype.Service;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.Constants;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 系统-配置-系统信息(SysConfigSystem)表服务实现类
 *
 * @author makejava
 * @since 2020-05-28 11:03:27
 */
@Service("sysConfigSystemService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"sysConfigSystem")
public class SysConfigSystemService extends BaseServiceImpl<SysConfigSystemEntity,SysConfigSystemMapper> {
   
}