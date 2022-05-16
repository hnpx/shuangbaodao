package cn.px.sys.modular.activity.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.activity.constant.TimeStatusEnum;
import cn.px.sys.modular.activity.vo.ActivityVo;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import jodd.util.URLDecoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class ActivityWrapper extends BaseWrapper<Map<String,Object>, ActivityVo> {
    @Override
    public ActivityVo wrap(Map<String, Object> item) {
        ActivityVo vo = new ActivityVo();
        BeanUtil.copyProperties(item, vo);
        if(vo.getStartTime().compareTo(new Date())==1){
            vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_TRUE.getValue());
        }else if(vo.getEndTime().compareTo(new Date())==1 && vo.getStartTime().compareTo(new Date())==-1){
            vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_FALSE.getValue());
        }else if (vo.getEndTime().compareTo(new Date())==-1) {
            vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_END.getValue());
        }
        try{
            String  strTest = URLDecoder.decode(item.get("introduction").toString(),"UTF-8");//解码
            vo.setIntroduction(strTest);
        }catch (Exception e){
            vo.setIntroduction("");
        }
        return vo;
    }
}
