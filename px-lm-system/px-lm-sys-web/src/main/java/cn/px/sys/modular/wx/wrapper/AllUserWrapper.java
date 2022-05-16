package cn.px.sys.modular.wx.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.doubleReport.mapper.ReportCadreMapper;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.member.vo.MemberVo;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.sys.modular.wx.vo.CadreVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
@Component
public class AllUserWrapper extends BaseWrapper<Map<String,Object>, CadreVo> {
    @Resource
    private ReportCadreMapper reportCadreMapper;
    @Override
    public CadreVo wrap(Map<String, Object> item) {
        CadreVo vo = new CadreVo();
        BeanUtil.copyProperties(item, vo);
        try{
            Long id=item.get("id")!=null ?Long.parseLong(item.get("id").toString()):null;
            if(id != null){
                vo.setName(reportCadreMapper.getReportCadreEntityByUserId(id).getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return vo;
    }
}
