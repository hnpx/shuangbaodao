package cn.px.sys.modular.activity.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.activity.vo.ActivityVo;
import cn.px.sys.modular.activity.vo.CommentVo;
import jodd.util.URLDecoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@Component
public class CommentWrapper extends BaseWrapper<Map<String,Object>, CommentVo> {
    @Override
    public CommentVo wrap(Map<String, Object> item) {
        CommentVo vo = new CommentVo();
        BeanUtil.copyProperties(item, vo);
         try{
             String  strTest = URLDecoder.decode(item.get("content").toString(),"UTF-8");//解码
             vo.setContent(strTest);
         }catch (Exception e){

         }
        return vo;
    }
}
