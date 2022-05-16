package cn.px.sys.modular.spike.service;

import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.spike.constant.TypeEnum1;
import cn.px.sys.modular.spike.entity.ProductEntity;
import cn.px.sys.modular.spike.entity.SpikeEntity;
import cn.px.sys.modular.spike.mapper.SpikeMapper;
import cn.px.sys.modular.spike.vo.SeckillActivityDetailsVO;
import cn.px.sys.modular.spike.vo.SpikeQueryVO;
import cn.px.sys.modular.spike.vo.SpVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 限时秒杀(Spike)表服务实现类
 *
 * @author makejava
 * @since 2020-09-07 16:03:34
 */
@Service("spikeService")
//@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"spike")
public class SpikeService extends BaseServiceImpl<SpikeEntity, SpikeMapper> {
    @Autowired
    SpikeMapper spikeMapper;
    @Resource
    private ProductService productService;

    /**
     * 查询限时秒杀
     * @param current
     * @param size
     * @param
     * @return
     */
    public Page timeLimitedSecondKill(Long current,Long size) {
        Page page = new Page<>(current, size);
        List<SpikeQueryVO> spikeEntityList = spikeMapper.timeLimitedSecondKill(page);
        return page.setRecords(spikeEntityList);
    }

    /**
     * 查询限时秒杀
     * @param
     * @param
     * @return
     */
    public Page timeLimitedSecondKill1(Integer timeStatus, String name){
        Page page = LayuiPageFactory.defaultPage();
        return page.setRecords(super.mapper.timeLimitedSecondKill1(page,timeStatus,name));


    }

    /**
     * 根据商品id查询是否为秒杀活动
     * @param pid
     * @return
     */
    public SpVo getSpikeEntityByPid(Long pid){
        SpVo spVo = new SpVo();
        List<SpikeEntity> spikeEntityList = super.mapper.getSpikeEntityByPid(pid);
        if(spikeEntityList.size() == 0){
         ProductEntity productEntity = productService.queryById(pid);
         spVo.setPrice(productEntity.getIntegral().intValue());
         spVo.setNumber(productEntity.getRemainingAmount());
         spVo.setRedeemedAmpunt(productEntity.getRedeemedAmpunt());
         spVo.setType(TypeEnum1.TYPE_ENUM_ONE.getValue());
         return spVo;
        }else {
            for (SpikeEntity spikeEntity:spikeEntityList) {
                if(spikeEntity.getEndTime().compareTo(new Date())==-1 || spikeEntity.getStratTime().compareTo(new Date())==1){
                    ProductEntity productEntity = productService.queryById(pid);
                    spVo.setPrice(productEntity.getIntegral().intValue());
                    spVo.setNumber(productEntity.getRemainingAmount());
                    spVo.setRedeemedAmpunt(productEntity.getRedeemedAmpunt());
                    spVo.setType(TypeEnum1.TYPE_ENUM_ONE.getValue());
                    return spVo;
                }else {
                    spVo.setPrice(spikeEntity.getPrice().intValue());
                    spVo.setNumber(spikeEntity.getNumber());
                    spVo.setRedeemedAmpunt(spikeEntity.getRedeemedAmpunt());

                    spVo.setType(TypeEnum1.TYPE_ENUM_TWO.getValue());
                    spVo.setNumberPeopleExchanged(spikeEntity.getNumberPeopleExchanged());
                    spVo.setId(spikeEntity.getId());
                    spVo.setStartTime(spikeEntity.getStratTime());
                    spVo.setEndTime(spikeEntity.getEndTime());
                    spVo.setLimitNumber(spikeEntity.getLimitNumber());
                    return spVo;
                }

            }
        }
        return spVo;

    }

    public void executeRedeemNow() {

    }



    /**
     * 查询秒杀活动详情
     * @param productId
     */
    public SeckillActivityDetailsVO getSeckillActivityDetails(Long productId) {
        return mapper.getSeckillActivityDetails(productId);
    }


    /**
     * 通过商品的id核对此商品是否进行秒杀  秒杀活动已结束
     * @param pid
     * @return
     */
    public SpikeEntity check(Long pid){
        return super.mapper.check(pid);
    }
    /**
     * 通过商品的id核对此商品是否进行秒杀  秒杀活动未开始 或 正在进行
     * @param pid
     * @return
     */
    public SpikeEntity check1(Long pid){
        return super.mapper.check1(pid);
    }
}