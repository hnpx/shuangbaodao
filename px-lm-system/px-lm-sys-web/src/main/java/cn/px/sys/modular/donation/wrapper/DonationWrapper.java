package cn.px.sys.modular.donation.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.donation.vo.DonationVo;
import cn.px.sys.modular.integral.vo.IntegralRecordVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DonationWrapper extends BaseWrapper<Map<String,Object>, DonationVo> {
    @Override
    public DonationVo wrap(Map<String, Object> item) {
        DonationVo vo = new DonationVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }
}
