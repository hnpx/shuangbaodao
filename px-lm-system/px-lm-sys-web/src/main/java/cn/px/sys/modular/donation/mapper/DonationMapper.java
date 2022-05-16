package cn.px.sys.modular.donation.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.donation.entity.DonationEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 捐赠管理(Donation)表数据库访问层
 *
 * @author
 * @since 2020-09-02 18:02:14
 */
public interface DonationMapper extends BaseMapperImpl<DonationEntity> {

    /**
     * 捐献记录
     * @param page
     * @param userId
     * @return
     */
    public Page<Map<String,Object>>  getHomeList(@Param("page") Page page,@Param("userId") Long userId);


}