package cn.px.sys.modular.homeClass.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.homeClass.vo.HomeClassVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HomeClassWrapper extends BaseWrapper<Map<String,Object>, HomeClassVo> {
    @Override
    public HomeClassVo wrap(Map<String, Object> item) {
        HomeClassVo vo = new HomeClassVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }

}
