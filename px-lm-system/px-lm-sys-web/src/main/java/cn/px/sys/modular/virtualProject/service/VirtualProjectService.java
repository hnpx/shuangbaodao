package cn.px.sys.modular.virtualProject.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.virtualProject.entity.VirtualProjectEntity;
import cn.px.sys.modular.virtualProject.mapper.VirtualProjectMapper;
import cn.px.sys.modular.virtualProject.vo.VirtualProjectExcelVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (VirtualProject)表服务实现类
 *
 * @author
 * @since 2020-12-16 09:38:31
 */
@Service("virtualProjectService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "virtualProject")
public class VirtualProjectService extends BaseServiceImpl<VirtualProjectEntity, VirtualProjectMapper> {


   public Page<Map<String,Object>> getList(String name,  Long belongingCommunity, Long belongingUnit,  Integer status,  Long companyClass){

        Page page = LayuiPageFactory.defaultPage();
        return super.mapper.getList(page,name,belongingCommunity,belongingUnit,status,companyClass);
    }

    public List<VirtualProjectExcelVo> getListExcel(Long belongingCommunity){
       return super.mapper.getListExcel(belongingCommunity);
    }


}