package cn.px.sys.modular.demand.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.demand.entity.DemandEntity;
import cn.px.sys.modular.demand.mapper.DemandMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 需求管理(Demand)表服务实现类
 *
 * @author
 * @since 2020-09-02 19:38:12
 */
@Service("demandService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "demand")
public class DemandService extends BaseServiceImpl<DemandEntity, DemandMapper> {

    @Resource
    private CommunityClassService communityClassService;
    /**
     * 需求查询列表
     * @param
     * @param name
     * @param status
     * @return
     */
   public Page<Map<String,Object>> getListPage( String name, Integer status,Long userId,String phone,Long demandClass){
        Page page = LayuiPageFactory.defaultPage();
       Long cid;
       if(userId == 1){
           cid = null;
       }else {
           CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
           cid =  communityClassEntity.getId();

       }
        return super.mapper.getListPage(page,name,status,cid,phone,demandClass);

    }

}