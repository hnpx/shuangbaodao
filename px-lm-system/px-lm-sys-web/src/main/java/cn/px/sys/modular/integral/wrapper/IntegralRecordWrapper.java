package cn.px.sys.modular.integral.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.integral.constant.TypeEnum;
import cn.px.sys.modular.integral.vo.IntegralRecordVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IntegralRecordWrapper extends BaseWrapper<Map<String,Object>, IntegralRecordVo> {



    @Override
    public IntegralRecordVo wrap(Map<String, Object> item) {
        IntegralRecordVo vo = new IntegralRecordVo();
        BeanUtil.copyProperties(item, vo);
        Integer type = Integer.parseInt(item.get("type").toString());
        Integer integral = Integer.parseInt(item.get("integral").toString());
        if(type.equals(TypeEnum.TYPE_ENUM_ONE.getValue())){
            vo.setIntegralType("+"+integral);
        }else if(type.equals(TypeEnum.TYPE_ENUM_TWO.getValue())) {
            vo.setIntegralType("-"+integral);
        }
        return vo;
    }



}
