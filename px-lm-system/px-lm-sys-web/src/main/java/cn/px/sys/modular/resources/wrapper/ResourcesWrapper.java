package cn.px.sys.modular.resources.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.project.vo.ProjectVo;
import cn.px.sys.modular.resources.vo.ResourcesVo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResourcesWrapper extends BaseWrapper<Map<String,Object>, ResourcesVo> {
    @Override
    public ResourcesVo wrap(Map<String, Object> item) {
        ResourcesVo vo = new ResourcesVo();
        BeanUtil.copyProperties(item, vo);
        return vo;
    }

}
