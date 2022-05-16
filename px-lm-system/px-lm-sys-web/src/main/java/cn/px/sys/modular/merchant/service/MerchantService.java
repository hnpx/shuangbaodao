package cn.px.sys.modular.merchant.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.homeClass.entity.HomeClassEntity;
import cn.px.sys.modular.homeClass.mapper.HomeClassMapper;
import cn.px.sys.modular.merchant.entity.MerchantEntity;
import cn.px.sys.modular.merchant.mapper.MerchantMapper;
import cn.px.sys.modular.merchant.vo.MerchantVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商家管理(Merchant)表服务实现类
 *
 * @author
 * @since 2020-09-02 14:37:41
 */
@Service("merchantService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "merchant")
public class MerchantService extends BaseServiceImpl<MerchantEntity, MerchantMapper> {

    @Resource
    private HomeClassMapper homeClassMapper;
    /**
     * 根据商家分类查询商家
     * @param page
     * @param classification
     * @return
     */
    public Page<Map<String,Object>> getListPage(Integer page,Integer pageSize,Long classification,String name,Integer status,String contactPerson,String phone){

        Page page1;
        if(pageSize == null){
            page1 = LayuiPageFactory.defaultPage();
        }else {
             page1 = new Page(page,pageSize);
        }
        return super.mapper.getListPage(page1,classification,name,status,contactPerson,phone);
    }


    /**
     * 根据商家分类查询商家
     * @param page
     * @param classification
     * @return
     */
    public Page<Map<String,Object>> getListPage1(Integer page,Integer pageSize,Long classification,String name){

        Page page1;
        if(pageSize == null){
            page1 = LayuiPageFactory.defaultPage();
        }else {
            page1 = new Page(page,pageSize);
        }
        return super.mapper.getListPage1(page1,classification,name);
    }

    /**
     * 爱心驿站详情
     * @param id
     * @return
     */
    public MerchantVo getMerchantVo(Long id){
       MerchantEntity merchantEntity =  super.mapper.selectById(id);
       MerchantVo merchantVo = new MerchantVo();
       BeanUtil.copyProperties(merchantEntity,merchantVo);
       HomeClassEntity homeClassEntity = homeClassMapper.selectById(merchantEntity.getBelongingHome());
        merchantVo.setBelongingHome(homeClassEntity.getName());
        return merchantVo;
    }

    /**
     * 通过用户id查询商家信息
     * @param userId
     * @return
     */
    public MerchantEntity getMerchantEntityByUserId(@Param("userId") Long userId){
        return super.mapper.getMerchantEntityByUserId(userId);
    }

    public MerchantEntity getById(Long id){
        return super.mapper.getById(id);
    }
}