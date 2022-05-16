package cn.px.sys.modular.activity.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.activity.vo.ActivityUserVo;
import cn.px.sys.modular.activity.vo.ActivityVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ActivityUserWrapper extends BaseWrapper<Map<String,Object>, ActivityUserVo> {
    @Override
    public ActivityUserVo wrap(Map<String, Object> item) {
        ActivityUserVo vo = new ActivityUserVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }
}
