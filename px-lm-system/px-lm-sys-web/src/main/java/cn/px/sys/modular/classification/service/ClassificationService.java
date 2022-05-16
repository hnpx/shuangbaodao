package cn.px.sys.modular.classification.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.classification.entity.ClassificationEntity;
import cn.px.sys.modular.classification.mapper.ClassificationMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * 商家分类管理(Classification)表服务实现类
 *
 * @author
 * @since 2020-08-27 15:21:47
 */
@Service("classificationService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "classification")
public class ClassificationService extends BaseServiceImpl<ClassificationEntity, ClassificationMapper> {

}