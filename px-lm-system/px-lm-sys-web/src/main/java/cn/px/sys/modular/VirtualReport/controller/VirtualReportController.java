package cn.px.sys.modular.VirtualReport.controller;


import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.AbstractController;

import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integralWater.entity.IntegralWaterEntity;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectEvaluateService;
import cn.px.sys.modular.project.service.ProjectService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.alibaba.fastjson.JSON;
import org.beetl.ext.fn.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/VirtualReport")
public class VirtualReportController extends AbstractController {

    @Autowired
    private ProjectService ProjectService;

    @Autowired
    private ProjectUserSignService signService;

    @Autowired
    private AllUserService allUserService;

    @Autowired
    private IntegralRecordService integralRecordService;
    @Autowired
    private IntegralWaterService integralWaterService;
    @Autowired
    private ProjectEvaluateService projectEvaluateService;
    @Autowired
    private CommunityClassService communityClassService;


    /*
        pc端虚拟上报
     */
    @PostMapping("/update")
    @Transactional
    public Object updatePC( ProjectEntity params){
        //创建项目
        Long userid = params.getUserId();
        CommunityClassEntity communityClassEntity =  communityClassService.getCommunityClassEntityByUserId(userid);
        Long uuid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        params.setEnable(4);
        params.setId(uuid);
        if(communityClassEntity == null){
            params.setBelongingCommunity(1L);
        }else {
            params.setBelongingCommunity(communityClassEntity.getId());
        }

        ProjectService.insertEntity(params);
        //给用户报名
        ProjectUserSignEntity entity = new ProjectUserSignEntity();
        entity.setProjectId(uuid);
        entity.setUserId(userid);
        entity.setEnable(2);
        entity.setCertificationStatus(2);
        signService.update(entity);
        //给用户增加积分
        updatePoints(userid,params.getIntegral(),params.getName());
        //双报到积分积分流水
        IntegralWaterEntity integralWaterEntity = new IntegralWaterEntity();
        integralWaterEntity.setUserId(userid);
        integralWaterEntity.setIntegral(Double.valueOf(params.getReportPoints()));
        integralWaterEntity.setIntegralDesc(params.getName());
        integralWaterService.update(integralWaterEntity);
        ProjectEvaluateEntity projectEvaluateEntity = new ProjectEvaluateEntity();
        projectEvaluateEntity.setContent("后台添加已被认证");
        projectEvaluateEntity.setCharityPoints(params.getIntegral());
        projectEvaluateEntity.setReportPoints(params.getReportPoints());
        projectEvaluateEntity.setUserId(userid);
        projectEvaluateEntity.setProjectId(uuid);
        projectEvaluateService.update(projectEvaluateEntity);
        return  setSuccessModelMap();
    }

    /*
        手机端虚拟上报 （后台审核）
     */
    @PostMapping("/updateP")
    public Object updatePhone(ProjectEntity params){
        //获取需求用户
        Long userid= LoginContextHolder.getContext().getUserId();
        CommunityClassEntity communityClassEntity =  communityClassService.getCommunityClassEntityByUserId(userid);
        Long uuid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        params.setId(uuid);
        params.setEnable(3);
        if(communityClassEntity == null){
            params.setBelongingCommunity(1L);
        }else {
            params.setBelongingCommunity(communityClassEntity.getId());
        }

        ProjectService.insertEntity(params);
        //给用户报名
        ProjectUserSignEntity entity = new ProjectUserSignEntity();
        entity.setProjectId(uuid);
        entity.setUserId(userid);
        entity.setEnable(2);
        signService.update(entity);
        return setSuccessModelMap();
    }

    /*
        审核通过
     */
    @PostMapping("/successs")
    public Object successs(ProjectEntity params){
        ProjectUserSignEntity entity = new ProjectUserSignEntity();
        entity.setId(params.getSignId());
        entity.setEnable(2);
        signService.update(entity);
        updatePoints(params.getUserId(),params.getIntegral(),params.getName());

        ProjectEntity entity1 = new ProjectEntity();
        entity1.setId(params.getId());
        entity1.setEnable(3);
        entity1.setIntegral(params.getIntegral());

        ProjectService.update(entity1);
        return setSuccessModelMap();
    }


    //给用户添加积分
    public void updatePoints(Long userId,Integer points,String name){
        AllUserEntity allUserEntity = new AllUserEntity();
        allUserEntity.setIntegral(points);
        allUserEntity.setId(userId);
        allUserService.updatePoints(allUserEntity);
        IntegralRecordEntity entity = new IntegralRecordEntity();
        entity.setIntegral(points);
        entity.setUserId(userId);
        entity.setSource(name);
        entity.setType(1);
        integralRecordService.update(entity);
    }



    @PostMapping("/updatePc")
    public Object updatePhone1(ProjectEntity params){
        //获取需求用户
        Long userid= params.getUserId();
        CommunityClassEntity communityClassEntity =  communityClassService.getCommunityClassEntityByUserId(userid);
        Long uuid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        params.setId(uuid);
        params.setEnable(3);
        if(communityClassEntity == null){
            params.setBelongingCommunity(1L);
        }else {
            params.setBelongingCommunity(communityClassEntity.getId());
        }

        ProjectService.insertEntity(params);
        //给用户报名
        ProjectUserSignEntity entity = new ProjectUserSignEntity();
        entity.setProjectId(uuid);
        entity.setUserId(userid);
        entity.setEnable(2);
        signService.update(entity);
        return setSuccessModelMap();
    }

}
