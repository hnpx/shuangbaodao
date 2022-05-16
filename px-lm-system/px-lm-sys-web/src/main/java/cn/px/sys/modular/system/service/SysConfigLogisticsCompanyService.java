package cn.px.sys.modular.system.service;

import cn.px.sys.modular.system.entity.SysConfigLogisticsCompanyEntity;
import cn.px.sys.modular.system.mapper.SysConfigLogisticsCompanyMapper;
import org.springframework.stereotype.Service;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.Constants;
import org.springframework.cache.annotation.CacheConfig;

/**
 * (SysConfigLogisticsCompany)表服务实现类
 *
 * @author makejava
 * @since 2020-05-28 09:28:17
 */
@Service("sysConfigLogisticsCompanyService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"sysConfigLogisticsCompany")
public class SysConfigLogisticsCompanyService extends BaseServiceImpl<SysConfigLogisticsCompanyEntity,SysConfigLogisticsCompanyMapper> {
   
}