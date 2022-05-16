package cn.px.sys.modular.resources.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.resources.vo.ResourcesManVo;
import cn.px.sys.modular.resources.vo.ResourcesUserVo;
import cn.px.sys.modular.resources.vo.ResourcesVo;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class ResourcesUserWrapper extends BaseWrapper<Map<String,Object>, ResourcesUserVo> {

    @Override
    public ResourcesUserVo wrap(Map<String, Object> item) {
        ResourcesUserVo vo = new ResourcesUserVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }

}
