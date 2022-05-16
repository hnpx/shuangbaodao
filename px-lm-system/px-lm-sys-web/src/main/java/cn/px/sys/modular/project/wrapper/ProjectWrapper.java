package cn.px.sys.modular.project.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.core.wrapper.BaseWrapper;
import cn.px.sys.modular.activity.constant.TimeStatusEnum;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.demandClass.service.DemandClassService;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.project.vo.ProjectVo;
import jodd.util.URLDecoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public class ProjectWrapper extends BaseWrapper<Map<String,Object>, ProjectVo> {

    @Resource
    private DemandClassService demandClassService;
    @Resource
    private ProjectUserSignService projectUserSignService;

    @Override
    public ProjectVo wrap(Map<String, Object> item) {
        ProjectVo vo = new ProjectVo();
        BeanUtil.copyProperties(item, vo);
        Long id1=item.get("companyClass")!=null ?Long.parseLong(item.get("companyClass").toString()):null;
        if(id1 != null){
            vo.setCompanyClassName(demandClassService.queryById(id1).getName());
        }
        Long id = item.get("id")!=null ?Long.parseLong(item.get("id").toString()):null;
       List<ProjectUserSignEntity> projectUserSignServiceList = projectUserSignService.getProjectUserSignEntityList(id);
      /* if(projectUserSignServiceList.size() != 0 ){
           vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_ENUM.getValue());
       }else {*/
           if(vo.getStartTime().compareTo(new Date())==1){
               vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_TRUE.getValue());
           } else if(vo.getEndTime().compareTo(new Date())==1 && vo.getStartTime().compareTo(new Date())==-1){
               if(projectUserSignServiceList.size() != 0){
                   vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_FALSE.getValue());
               }else {
                   vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_ENUM.getValue());
               }

           } else if(vo.getEndTime().compareTo(new Date())==-1) {
              vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_FALSE.getValue());
           }
           if (StringUtils.isNotEmpty(vo.getCertification())){
               vo.setTimeStatus(TimeStatusEnum.TIME_STATUS_END.getValue());
           }


        try{
            String  strTest = URLDecoder.decode(item.get("content").toString(),"UTF-8");//解码
            vo.setContent(strTest);
        }catch (Exception e){
            vo.setContent("");
        }
       //}


        return vo;
    }
}
