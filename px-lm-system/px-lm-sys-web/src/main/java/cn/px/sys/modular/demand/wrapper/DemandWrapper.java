package cn.px.sys.modular.demand.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.demand.vo.DemandVo;
import cn.px.sys.modular.demandClass.service.DemandClassService;
import cn.px.sys.modular.merchant.vo.MerchantVo;
import jodd.util.URLDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
@Component
public class DemandWrapper extends BaseWrapper<Map<String,Object>, DemandVo> {

    @Resource
    private DemandClassService demandClassService;
    @Override
    public DemandVo wrap(Map<String, Object> item) {
        DemandVo vo = new DemandVo();
        BeanUtil.copyProperties(item, vo);
        try{
            Long  demandClass = Long.parseLong(item.get("demandClass").toString());
            vo.setDemandClassName(demandClassService.queryById(demandClass).getName());
        }catch (Exception e){
            vo.setDemandClassName("");
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
