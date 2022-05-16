package cn.px.sys.modular.doubleReport.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
@Component
public class UnitWrapper extends BaseWrapper<Map<String,Object>, UnitListVo> {

    @Resource
    private IntegralWaterService integralWaterService;
    @Resource
    private CommunityClassService communityClassService;
    @Override
    public UnitListVo wrap(Map<String, Object> item) {
        UnitListVo vo = new UnitListVo();
        BeanUtil.copyProperties(item, vo);
        Long id=item.get("belongingCommunity")!=null ?Long.parseLong(item.get("belongingCommunity").toString()):null;
         if(id != null){
             vo.setBelongingCommunityName(communityClassService.queryById(id).getName());
         }
        Long userId=item.get("userId")!=null ?Long.parseLong(item.get("userId").toString()):null;
         if(userId != null){
            int showIntegral = integralWaterService.showIntegral(userId,null);
            vo.setShowIntegral(showIntegral);
            vo.setType(1); //绑定
         }else {
             vo.setShowIntegral(0);
             vo.setType(0);  //未绑定

         }


        return vo;
    }
}
