/**
 *
 */
package cn.px.base.core;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;


/**
 *
 * @author PXHLT
 *
 * @version 2016年6月3日 下午2:30:14
 *
 */
public interface BaseMapperImpl<T extends BaseModel> extends BaseMapper<T> {

    List<Long> selectIdPage(@Param("cm") T params);

    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    List<Long> selectIdPage(Page<Long> page, @Param("cm") Map<String, Object> params);

    List<Long> selectIdPage(Page<Long> page, @Param("cm") T params);

    List<T> selectPage(Page<Long> page, @Param("cm") Map<String, Object> params);

    Integer selectCount(@Param("cm") Map<String, Object> params);
}
