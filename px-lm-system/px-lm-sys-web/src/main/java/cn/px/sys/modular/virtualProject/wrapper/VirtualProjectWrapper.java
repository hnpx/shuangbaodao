package cn.px.sys.modular.virtualProject.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.demandClass.entity.DemandClassEntity;
import cn.px.sys.modular.demandClass.service.DemandClassService;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.resources.vo.ResourcesManVo;
import cn.px.sys.modular.serviceClass.service.ServiceClassService;
import cn.px.sys.modular.virtualProject.vo.VirtualProjectVo;
import jodd.util.URLDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class VirtualProjectWrapper extends BaseWrapper<Map<String,Object>, VirtualProjectVo> {
   @Resource
   private CommunityClassService communityClassService;
   @Resource
   private UnitService unitService;
   @Resource
   private DemandClassService demandClassService;
    @Override
    public VirtualProjectVo wrap(Map<String, Object> item) {
        VirtualProjectVo vo = new VirtualProjectVo();
        BeanUtil.copyProperties(item, vo);
       try {
           Long belongingCommunity =  Long.parseLong(item.get("belongingCommunity").toString());
           CommunityClassEntity communityClassEntity = communityClassService.queryById(belongingCommunity);
           vo.setBelongingCommunityName(communityClassEntity.getName());
       }catch (Exception e){
           vo.setBelongingCommunityName("");
       }

       try{
           Long belongingUnit =  Long.parseLong(item.get("belongingUnit").toString());
           UnitEntity unitEntity = unitService.queryById(belongingUnit);
           vo.setBelongingUnitName(unitEntity.getName());

       }catch (Exception e){
           vo.setBelongingUnitName("");
       }

    try{

        Long companyClass = Long.parseLong(item.get("companyClass").toString());
        DemandClassEntity demandClassEntity = demandClassService.queryById(companyClass);
        vo.setCompanyClassName(demandClassEntity.getName());
    }catch (Exception e){
        vo.setCompanyClassName("");
    }

        return vo;
    }

}
