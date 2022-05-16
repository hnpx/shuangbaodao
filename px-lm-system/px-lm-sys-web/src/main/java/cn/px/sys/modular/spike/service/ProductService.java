package cn.px.sys.modular.spike.service;

import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.spike.entity.ProductEntity;
import cn.px.sys.modular.spike.mapper.ProductMapper;
import cn.px.sys.modular.spike.vo.PointsExchangeDetailsVO;
import cn.px.sys.modular.spike.vo.ProductVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品管理(Product)表服务实现类
 *
 * @author makejava
 * @since 2020-09-07 16:27:32
 */
@Service("productService")
//@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"product")
public class ProductService extends BaseServiceImpl<ProductEntity, ProductMapper> {


    /**
     * 查询积分兑换
     * @param current
     */
    public Page pointsExchange(Long current,Long size) {
        Page page=new Page(current,size);
       return page.setRecords(mapper.pointsExchange(page));

    }

    /**
     * 查询积分兑换详情
     */
    public PointsExchangeDetailsVO getPointsExchangeDetails(Long id) {
        return mapper.getPointsExchangeDetails(id);
    }
}