package cn.px.sys.modular.activity.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.activity.vo.ActivityUserVo;
import cn.px.sys.modular.activity.vo.ActivityVo;
import cn.px.sys.modular.activity.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class UserWrapper extends BaseWrapper<Map<String,Object>, UserVo> {
    @Override
    public UserVo wrap(Map<String, Object> item) {
        UserVo vo = new UserVo();
        BeanUtil.copyProperties(item, vo);

        return vo;
    }
}
