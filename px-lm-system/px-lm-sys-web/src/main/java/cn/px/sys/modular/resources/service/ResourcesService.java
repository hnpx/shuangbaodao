package cn.px.sys.modular.resources.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.resources.entity.ResourcesEntity;
import cn.px.sys.modular.resources.mapper.ResourcesMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 资源管理(Resources)表服务实现类
 *
 * @author
 * @since 2020-09-04 12:32:32
 */
@Service("resourcesService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "resources")
public class ResourcesService extends BaseServiceImpl<ResourcesEntity, ResourcesMapper> {

    @Resource
    private CommunityClassService communityClassService;

    /**
     * 资源列表
     * @return
     */
    public Page<Map<String,Object>> getPageList(Integer page,Integer pageSize,Long resourcesClass){
        Page page1 = new Page(page,pageSize);
        Date date = new Date();
        return super.mapper.getPageList(page1,date,resourcesClass);
    }

    /**
     * 资源列表后台
     * @return
     */
    public Page<Map<String,Object>> getPageListMan(String name,Integer status,Long userId,String contactPerson,String phone){
        Page page = LayuiPageFactory.defaultPage();
        Long cid;
        if(userId == 1){
            cid = null;
        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
            cid =  communityClassEntity.getId();

        }

        return super.mapper.getPageListMan(page,name,status,cid,contactPerson,phone);
    }

    /**
     * 我的资源
     * @return
     */
    public Page<Map<String,Object>> getPageListMySelf(Integer page,Integer pageSize,Long userId){
        Page page1 = new Page(page,pageSize);
        return super.mapper.getPageListMySelf(page1,userId);
    }
}