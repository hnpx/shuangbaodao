package cn.px.sys.modular.spike.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.merchant.vo.MerchantVo;
import cn.px.sys.modular.spike.vo.OrderVo;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class OrderWrapper  extends BaseWrapper<Map<String,Object>, OrderVo> {
    @Override
    public OrderVo wrap(Map<String, Object> item) {
        OrderVo vo = new OrderVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }
}
