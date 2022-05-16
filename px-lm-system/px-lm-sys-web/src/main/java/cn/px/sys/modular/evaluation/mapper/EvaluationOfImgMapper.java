package cn.px.sys.modular.evaluation.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.evaluation.entity.EvaluationOfImgEntity;

import java.util.List;
import java.util.Map;

/**
 * (EvaluationOfImg)表数据库访问层
 *
 * @author
 * @since 2020-09-16 09:53:37
 */
public interface EvaluationOfImgMapper extends BaseMapperImpl<EvaluationOfImgEntity> {


    List<?> getImgUrl();

}