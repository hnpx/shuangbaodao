package cn.px.sys.modular.spike.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.spike.entity.ProductEntity;
import cn.px.sys.modular.spike.vo.PointsExchangeDetailsVO;
import cn.px.sys.modular.spike.vo.ProductVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品管理(Product)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-07 16:27:32
 */
public interface ProductMapper extends BaseMapperImpl<ProductEntity> {

    /**
     * 查询积分兑换列表
     * @param page
     * @return
     */
    List<ProductVO> pointsExchange(Page page);

   PointsExchangeDetailsVO getPointsExchangeDetails(Long id);
}