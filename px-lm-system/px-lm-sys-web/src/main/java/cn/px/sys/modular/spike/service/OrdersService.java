package cn.px.sys.modular.spike.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.spike.entity.OrdersEntity;
import cn.px.sys.modular.spike.mapper.OrdersMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 兑换订单(Orders)表服务实现类
 *
 * @author
 * @since 2020-09-09 09:25:39
 */
@Service("ordersService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "orders")
public class OrdersService extends BaseServiceImpl<OrdersEntity, OrdersMapper> {

    /**
     * 查询订单列表
     * @param
     * @param nickname
     * @param productName
     * @return
     */
   public Page<Map<String,Object>> getHomeList(String nickname, String productName,Integer status){
        Page page = LayuiPageFactory.defaultPage();
        return super.mapper.getHomeList(page,nickname,productName,status);
    }

}