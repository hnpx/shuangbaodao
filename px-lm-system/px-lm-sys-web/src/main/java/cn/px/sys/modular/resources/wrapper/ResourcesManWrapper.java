package cn.px.sys.modular.resources.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.demandClass.service.DemandClassService;
import cn.px.sys.modular.resources.vo.ResourcesManVo;
import cn.px.sys.modular.resources.vo.ResourcesVo;
import cn.px.sys.modular.serviceClass.service.ServiceClassService;
import jodd.util.URLDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class ResourcesManWrapper extends BaseWrapper<Map<String,Object>, ResourcesManVo> {
    @Resource
    private ServiceClassService ServiceClassService;
    @Override
    public ResourcesManVo wrap(Map<String, Object> item) {
        ResourcesManVo vo = new ResourcesManVo();
        BeanUtil.copyProperties(item, vo);
        Long id=item.get("resourcesClass")!=null ?Long.parseLong(item.get("resourcesClass").toString()):null;
        if(id != null){
            vo.setResourcesClassName(ServiceClassService.queryById(id).getName());
        }
        try{
            String  strTest = URLDecoder.decode(item.get("content").toString(),"UTF-8");//解码
            vo.setContent(strTest);
        }catch (Exception e){
            vo.setContent("");
        }

        return vo;
    }

}
