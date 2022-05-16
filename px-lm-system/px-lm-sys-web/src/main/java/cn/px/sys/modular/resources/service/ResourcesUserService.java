package cn.px.sys.modular.resources.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.resources.entity.ResourcesUserEntity;
import cn.px.sys.modular.resources.mapper.ResourcesUserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * (ResourcesUser)表服务实现类
 *
 * @author
 * @since 2020-09-04 15:40:50
 */
@Service("resourcesUserService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "resourcesUser")
public class ResourcesUserService extends BaseServiceImpl<ResourcesUserEntity, ResourcesUserMapper> {

    /**
     * 查询申请资源列表
     * @param
     * @param name
     * @return
     */
    public Page<Map<String,Object>> getHomeList(Integer page,Integer pageSize,String name,Long rid){
        Page page1;
        if(pageSize == null){
             page1 = LayuiPageFactory.defaultPage();
        }else {
            page1 = new Page(page,pageSize);
        }
        return super.mapper.getHomeList(page1,name,rid);
    }

}