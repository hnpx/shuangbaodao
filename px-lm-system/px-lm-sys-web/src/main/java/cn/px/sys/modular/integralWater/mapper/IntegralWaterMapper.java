package cn.px.sys.modular.integralWater.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.integralWater.entity.IntegralWaterEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (IntegralWater)表数据库访问层
 *
 * @author
 * @since 2020-09-28 11:14:19
 */
public interface IntegralWaterMapper extends BaseMapperImpl<IntegralWaterEntity> {
    Page<Map<String,Object>> getlist(@Param("page") Page page, @Param("uid") Long id);

    /**
     * 表现积分
     * @param userId
     * @return
     */
    public int showIntegral(@Param("userId") Long userId,@Param("year") String year);


     IntegralWaterEntity getIntegralWaterByUserScore(@Param("userId") Long userId,@Param("IntegralDesc") String IntegralDesc,@Param("integral") Integer integral);


     public List<IntegralWaterEntity> getListByUser(@Param("userId") Long userId);


}