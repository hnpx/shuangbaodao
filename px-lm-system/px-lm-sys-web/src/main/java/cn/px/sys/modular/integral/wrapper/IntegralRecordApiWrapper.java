package cn.px.sys.modular.integral.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.integral.constant.TypeEnum;
import cn.px.sys.modular.integral.vo.IntegralRecordApiVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IntegralRecordApiWrapper  extends BaseWrapper<Map<String,Object>, IntegralRecordApiVo> {


    @Override
    public IntegralRecordApiVo wrap(Map<String, Object> item) {
        IntegralRecordApiVo vo = new IntegralRecordApiVo();
        BeanUtil.copyProperties(item, vo);
        try{
            Integer type = Integer.parseInt(item.get("type").toString());
            Integer integral = Integer.parseInt(item.get("integral").toString());
            if(type.equals(TypeEnum.TYPE_ENUM_ONE.getValue())){
                vo.setIntegralType("+"+integral);
            }else if(type.equals(TypeEnum.TYPE_ENUM_TWO.getValue())) {
                vo.setIntegralType("-"+integral);
            }
        }catch (Exception e){

        }

        return vo;
    }
}
