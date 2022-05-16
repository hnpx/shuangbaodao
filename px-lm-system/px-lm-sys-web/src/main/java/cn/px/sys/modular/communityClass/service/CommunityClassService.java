package cn.px.sys.modular.communityClass.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.mapper.CommunityClassMapper;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.doubleReport.vo.UnitVo;
import cn.px.sys.modular.homeClass.entity.HomeClassEntity;
import cn.px.sys.modular.homeClass.service.HomeClassService;
import cn.px.sys.modular.homeClass.vo.HomeClassByVo;
import cn.px.sys.modular.homeClass.vo.HomeClassVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CommunityClass)表服务实现类
 *
 * @author
 * @since 2020-08-27 16:02:55
 */
@Service("communityClassService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "communityClass")
public class CommunityClassService extends BaseServiceImpl<CommunityClassEntity, CommunityClassMapper> {

    @Resource
    private HomeClassService homeClassService;
    @Resource
    private UnitService unitService;

    /**
     * 根据用户id查询社区信息
     * @param userId
     * @return
     */
    public CommunityClassEntity getCommunityClassEntityByUserId(Long userId){
        return super.mapper.getCommunityClassEntityByUserId(userId);
    }

    /**
     * 查询该社区下是否绑定有信息
     * @param cid
     * @return
     */
    public int check(Long cid){

      List<HomeClassByVo> homeClassVoList  =  homeClassService.getHomeClassByCommunityId(cid);
      if(homeClassVoList.size() !=0){
          return 1;
      }
        List<UnitVo> unitVoList = unitService.getUnitEntityByCommunity(cid);
      if(unitVoList.size() != 0){
          return 1;
      }
      return 0;

    }

}