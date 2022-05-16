package cn.px.sys.modular.merchant.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.merchant.vo.MerchantVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class MerchantWrapper  extends BaseWrapper<Map<String,Object>, MerchantVo> {
    @Resource
    private IntegralRecordService integralRecordService;
    @Override
    public MerchantVo wrap(Map<String, Object> item) {
        MerchantVo vo = new MerchantVo();
        BeanUtil.copyProperties(item, vo);
          Integer integral =  integralRecordService.getIntegral(Long.parseLong(item.get("id").toString()));
          vo.setIntegral(integral);
        return vo;
    }
}
