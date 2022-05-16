package cn.px.sys.modular.spike.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.spike.entity.SpikeEntity;
import cn.px.sys.modular.spike.vo.SeckillActivityDetailsVO;
import cn.px.sys.modular.spike.vo.SpikeQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * 限时秒杀(Spike)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-07 16:03:32
 */
public interface SpikeMapper extends BaseMapperImpl<SpikeEntity> {
    /**
     * 查询限时秒杀
     * @param page
     * @param
     * @return
     */
    List<SpikeQueryVO> timeLimitedSecondKill(Page page);
    /**
     * 查询限时秒杀
     * @param page
     * @param
     * @return
     */
    List<SpikeQueryVO> timeLimitedSecondKill1(@Param("page") Page page, @Param("timeStatus") Integer timeStatus,@Param("name") String name);

    /**
     * 根据商品id查询是否为秒杀活动
     * @param pid
     * @return
     */
    public List<SpikeEntity> getSpikeEntityByPid(@Param("pid") Long pid);


    /**
     * 查询秒杀活动详情
     * @param productId
     */
    SeckillActivityDetailsVO getSeckillActivityDetails(Long productId);

    /**
     * 通过商品的id核对此商品是否进行秒杀   秒杀活动是否结束
     * @param pid
     * @return
     */
    public SpikeEntity check(@Param("pid") Long pid);

    /**
     * 通过商品的id核对此商品是否进行秒杀   秒杀活动是否未开始或者进行中
     * @param pid
     * @return
     */
    public SpikeEntity check1(@Param("pid") Long pid);

}