package cn.px.sys.modular.wx.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseApiWrapper;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.mapper.ReportCadreMapper;
import cn.px.sys.modular.doubleReport.mapper.UnitMapper;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.mapper.MemberMapper;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.wx.vo.CadreVo;
import cn.px.sys.modular.wx.vo.UserReVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class UserReWrapper extends BaseApiWrapper<Map<String,Object>, UserReVo> {

    @Resource
    private MemberMapper memberMapper;
    @Resource
    private ReportCadreMapper reportCadreMapper;
    @Resource
    private UnitMapper unitMapper;
    @Override
    public UserReVo wrap(Map<String, Object> item,Long id) {
        UserReVo vo = new UserReVo();
        BeanUtil.copyProperties(item, vo);
        vo.setActivityId(id);
        Long userId = Long.parseLong( item.get("userId").toString());
        if(userId != null){
            Integer type = Integer.parseInt(item.get("type").toString());
            if(type != null){
                switch (type){
                    case 1:
                        MemberEntity memberEntity =  memberMapper.getMemberEntityByPhone(userId);
                      if(memberEntity != null){
                          vo.setName(memberEntity.getName());
                      }else {
                          vo.setName("");
                      }
                        break;
                    case 2:
                     ReportCadreEntity reportCadreEntity = reportCadreMapper.getReportCadreEntityByUserId(userId);
                    if(reportCadreEntity != null){
                        vo.setName(reportCadreEntity.getName());
                    }else {
                        vo.setName(reportCadreEntity.getName());
                    }
                        break;
                    case 3:
                       UnitEntity unitEntity = unitMapper.getUnitEntityByUserId(userId);
                       if(unitEntity !=null){
                           vo.setName(unitEntity.getName());
                       }else {
                           vo.setName("");
                       }

                        break;
                    default:
                        break;
                }
            }
        }

        return vo;
    }
}
