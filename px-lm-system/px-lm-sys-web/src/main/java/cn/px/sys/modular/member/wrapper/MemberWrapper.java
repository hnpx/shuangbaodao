package cn.px.sys.modular.member.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.demand.vo.DemandVo;
import cn.px.sys.modular.member.vo.MemberVo;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class MemberWrapper extends BaseWrapper<Map<String,Object>, MemberVo> {

    @Override
    public MemberVo wrap(Map<String, Object> item) {
        MemberVo vo = new MemberVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }
}
