package cn.px.sys.modular.mechanism.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.mechanism.entity.MechanismEntity;
import cn.px.sys.modular.mechanism.mapper.MechanismMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 机构管理(Mechanism)表服务实现类
 *
 * @author
 * @since 2020-09-01 11:25:50
 */
@Service("mechanismService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "mechanism")
public class MechanismService extends BaseServiceImpl<MechanismEntity, MechanismMapper> {

    /**
     * 查询列表
     * @param
     * @return
     */
    public Page<Map<String,Object>> getList(Integer page,Integer pageSize){
        Page page1 = new Page(page,pageSize);
        return super.mapper.getList(page1);
    }


}