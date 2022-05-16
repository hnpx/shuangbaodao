package cn.px.sys.modular.spike.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.spike.entity.OrdersEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

/**
 * 兑换订单(Orders)表数据库访问层
 *
 * @author
 * @since 2020-09-09 09:25:52
 */
public interface OrdersMapper extends BaseMapperImpl<OrdersEntity> {
    /**
     * 查询订单列表
     * @param page
     * @param nickname
     * @param productName
     * @return
     */
    Page<Map<String,Object>> getHomeList(@Param("page") Page page,@Param("nickname") String nickname,@Param("productName") String productName,@Param("status") Integer status);

    /**
     * 查询每个人进行秒杀兑换数
     * @return
     */
    public int count(@Param("productId") Long productId,@Param("userId") Long userId,@Param("startTime") Date startTime,@Param("endTime") Date endTime );
}