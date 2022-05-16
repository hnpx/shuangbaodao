package cn.px.sys.modular.donation.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.donation.entity.DonationEntity;
import cn.px.sys.modular.donation.mapper.DonationMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 捐赠管理(Donation)表服务实现类
 *
 * @author
 * @since 2020-09-02 18:02:03
 */
@Service("donationService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "donation")
public class DonationService extends BaseServiceImpl<DonationEntity, DonationMapper> {


    /**
     * 捐献记录
     * @param
     * @param userId
     * @return
     */
    public Page<Map<String,Object>> getHomeList(Integer page,Integer pageSize,Long userId){
        Page page1 = new Page(page,pageSize);
        return super.mapper.getHomeList(page1,userId);
    }

}