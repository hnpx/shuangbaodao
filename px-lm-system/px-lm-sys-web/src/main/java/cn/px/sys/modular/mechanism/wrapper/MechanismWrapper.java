package cn.px.sys.modular.mechanism.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.mechanism.vo.MechanismVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MechanismWrapper extends BaseWrapper<Map<String,Object>, MechanismVo> {

    @Override
    public MechanismVo wrap(Map<String, Object> item) {
        MechanismVo vo = new MechanismVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }
}
