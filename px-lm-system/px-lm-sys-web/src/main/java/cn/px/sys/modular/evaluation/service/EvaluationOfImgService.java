package cn.px.sys.modular.evaluation.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.evaluation.entity.EvaluationOfImgEntity;
import cn.px.sys.modular.evaluation.mapper.EvaluationOfImgMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (EvaluationOfImg)表服务实现类
 *
 * @author
 * @since 2020-09-16 09:53:18
 */
@Service("evaluationOfImgService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "evaluationOfImg")
public class EvaluationOfImgService extends BaseServiceImpl<EvaluationOfImgEntity, EvaluationOfImgMapper> {


   public  Object getImgUrl(){
            return super.mapper.getImgUrl().get(0);
    }
}