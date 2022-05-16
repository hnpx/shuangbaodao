package cn.px.sys.modular.merchant.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.merchant.entity.MerchantEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 商家管理(Merchant)表数据库访问层
 *
 * @author
 * @since 2020-09-02 14:37:54
 */
public interface MerchantMapper extends BaseMapperImpl<MerchantEntity> {
    /**
     * 根据商家分类查询商家
     * @param page
     * @param classification
     * @return
     */
    public Page<Map<String,Object>> getListPage(@Param("page") Page page,@Param("classification") Long classification,@Param("name") String name,@Param("status") Integer status,
                                                @Param("contactPerson") String contactPerson,@Param("phone") String phone);


    /**
     * 根据商家分类查询商家
     * @param page
     * @param classification
     * @return
     */
    public Page<Map<String,Object>> getListPage1(@Param("page") Page page,@Param("classification") Long classification,@Param("name") String name);
    /**
     * 通过用户id查询商家信息
     * @param userId
     * @return
     */
    public MerchantEntity getMerchantEntityByUserId(@Param("userId") Long userId);

    public MerchantEntity getById(@Param("id") Long id);
}