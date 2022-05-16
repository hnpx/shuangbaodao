package cn.px.sys.modular.donation.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.donation.entity.DonationDescEntity;
import cn.px.sys.modular.donation.mapper.DonationDescMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * (DonationDesc)表服务实现类
 *
 * @author
 * @since 2020-09-02 18:38:50
 */
@Service("donationDescService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "donationDesc")
public class DonationDescService extends BaseServiceImpl<DonationDescEntity, DonationDescMapper> {
    /**
     * 查询捐赠介绍
     * @param type
     * @return
     */
    public DonationDescEntity getDesc(Integer type){
        DonationDescEntity donationDescEntity = new DonationDescEntity();
        donationDescEntity.setType(type);
        Wrapper<DonationDescEntity> qw = new QueryWrapper<>(donationDescEntity);
        DonationDescEntity donationDescEntity1 =super.mapper.selectOne(qw);
        return donationDescEntity1;
    }
}