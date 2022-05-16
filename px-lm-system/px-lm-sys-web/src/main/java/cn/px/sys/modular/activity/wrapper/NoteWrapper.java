package cn.px.sys.modular.activity.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.activity.vo.NoteVo;
import cn.px.sys.modular.activity.vo.UserVo;
import cn.px.sys.modular.wx.service.AllUserService;
import jodd.util.URLDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
@Component
public class NoteWrapper extends BaseWrapper<Map<String,Object>, NoteVo> {

    @Resource
    private AllUserService allUserService;
    @Override
    public NoteVo wrap(Map<String, Object> item) {
        NoteVo vo = new NoteVo();
        BeanUtil.copyProperties(item, vo);
        try {
       Long  userId =  Long.parseLong(item.get("createBy").toString());
        vo.setApplyName(allUserService.getName(userId).getName());

        }catch (Exception e){

        }

        try{
            String  strTest = URLDecoder.decode(item.get("name").toString(),"UTF-8");//解码
            vo.setName(strTest);
        }catch (Exception e){
            vo.setName("");
        }
        return vo;
    }
}
