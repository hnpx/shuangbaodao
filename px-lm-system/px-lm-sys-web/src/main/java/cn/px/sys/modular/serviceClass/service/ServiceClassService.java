package cn.px.sys.modular.serviceClass.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.serviceClass.entity.ServiceClassEntity;
import cn.px.sys.modular.serviceClass.mapper.ServiceClassMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * (ServiceClass)表服务实现类
 *
 * @author
 * @since 2020-08-27 17:49:40
 */
@Service("serviceClassService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "serviceClass")
public class ServiceClassService extends BaseServiceImpl<ServiceClassEntity, ServiceClassMapper> {

}