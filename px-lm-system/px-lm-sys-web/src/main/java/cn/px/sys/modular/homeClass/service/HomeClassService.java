package cn.px.sys.modular.homeClass.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.homeClass.entity.HomeClassEntity;
import cn.px.sys.modular.homeClass.mapper.HomeClassMapper;
import cn.px.sys.modular.homeClass.vo.HomeClassByVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (HomeClass)表服务实现类
 *
 * @author
 * @since 2020-08-27 16:15:35
 */
@Service("homeClassService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "homeClass")
public class HomeClassService extends BaseServiceImpl<HomeClassEntity, HomeClassMapper> {

    /**
     * 查询小区列表

     * @param name 小区名称
     * @param cname 社区名称
     * @return
     */
   public Page<Map<String,Object>> getHomeClass(String name,String cname,Long cid){
       Page page = LayuiPageFactory.defaultPage();
       return super.mapper.getHomeClass(page,name,cname,cid);
   }

    /**
     * 通过社区id查询所有的小区
     * @param communityId
     * @return
     */
   public List<HomeClassByVo> getHomeClassByCommunityId(@Param("communityId") Long communityId){
      List<HomeClassEntity> homeClassEntityList = super.mapper.getHomeClassByCommunityId(communityId);
      List<HomeClassByVo> homeClassByVoList = new ArrayList<>();
       for (HomeClassEntity homeClassEntity:homeClassEntityList) {
           HomeClassByVo homeClassByVo = new HomeClassByVo();
           BeanUtil.copyProperties(homeClassEntity,homeClassByVo);
           homeClassByVoList.add(homeClassByVo);
       }
       return homeClassByVoList;
   }


   public Map<String,Object> HomeTotal(){
       return super.mapper.HomeTotal();
   }
}