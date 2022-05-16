package cn.px.sys.modular.activity.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.activity.constant.SignEnum;
import cn.px.sys.modular.activity.constant.SignIsOrNotEnum;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.mapper.ActivityUserSignMapper;
import cn.px.sys.modular.activity.vo.UserVo;
import cn.px.sys.modular.integral.constant.TypeEnum;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.mapper.ProjectUserSignMapper;
import cn.px.sys.modular.project.service.ProjectService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (ActivityUserSign)表服务实现类
 *
 * @author
 * @since 2020-08-31 11:08:06
 */
@Service("activityUserSignService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "activityUserSign")
public class ActivityUserSignService extends BaseServiceImpl<ActivityUserSignEntity, ActivityUserSignMapper> {

    @Resource
    private ActivityService activityService;
    @Resource
    private AllUserService allUserService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private ProjectUserSignService projectUserSignService;
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectUserSignMapper projectUserSignMapper;

    /**
     * 我参与的活动列表
     * @param userId
     * @return
     */
    public Page<Map<String,Object>> getListByUserId(Integer page,Integer pageSize,Long userId,Integer status){
        Page page1 = new Page(page,pageSize);
        Date date = new Date();
        return super.mapper.getListByUserId(page1,userId,date,status);
    }

    /**
     * 通过用户id查新报名列表
     * @param page
     * @param activeId
     * @return
     */
    public Page<Map<String,Object>> getListSignByUserId(Integer page,Integer pageSize, Long activeId){
        Page page1;
        if(pageSize != null){
             page1 = new Page(page,pageSize);
        }else {
            page1 = LayuiPageFactory.defaultPage();
        }

        return super.mapper.getListSignByUserId(page1,activeId);
    }

    public List<UserVo> getListSignBy(Long activeId){
        return super.mapper.getListSignBy(activeId);
    }

    /**
     * 活动结束自动给用户加积分
     * @param
     * @return
     */
   // @Transactional
    public void getIntegeral(){
       List<ActivityEntity> activityEntityList = activityService.getList();
        for (ActivityEntity activityEntity :activityEntityList) {
            if(activityEntity.getSignIn().equals(SignIsOrNotEnum.IS_SIGN_ENUM_FALSE.getValue())){
                List<ActivityUserSignEntity> activityUserSignEntityList =  super.mapper.getListSignByAid(activityEntity.getId());
                for (ActivityUserSignEntity activityUserSignEntity:activityUserSignEntityList) {
                    AllUserEntity allUserEntity =   allUserService.queryById(activityUserSignEntity.getUserId());
                    allUserEntity.setIntegral(allUserEntity.getIntegral()+activityEntity.getIntegral());
                    allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+activityEntity.getIntegral());
                    IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                    integralRecordEntity.setIntegral(activityEntity.getIntegral());
                    integralRecordEntity.setUserId(activityUserSignEntity.getUserId());
                    integralRecordEntity.setSource("参与活动");
                    integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                    integralRecordService.update(integralRecordEntity);
                    allUserService.update(allUserEntity);
                }
            }
            activityEntity.setSignIn(SignIsOrNotEnum.IS_SIGN_ENUM_TRUE.getValue());
            activityService.update(activityEntity);
        }
    }

    /**
     * 活动结束自动给用户加积分
     * @param
     * @return
     */
    //@Transactional
    public void getIntegeral(Long aid){

                  ActivityEntity activityEntity =  activityService.queryById(aid);
                  if(activityEntity.getSignIn().equals(1)){ //是否签到（1.是2.否）
                      List<ActivityUserSignEntity> activityUserSignEntityList =  super.mapper.getListSignByAid(aid);
                      for (ActivityUserSignEntity activityUserSignEntity:activityUserSignEntityList) {
                          if(activityUserSignEntity.getStatus().equals(0)){
                              continue;
                          }else {
                              AllUserEntity allUserEntity =   allUserService.queryById(activityUserSignEntity.getUserId());
                              allUserEntity.setIntegral(allUserEntity.getIntegral()+activityEntity.getIntegral());
                              allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+activityEntity.getIntegral());
                              IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                              integralRecordEntity.setIntegral(activityEntity.getIntegral());
                              integralRecordEntity.setUserId(activityUserSignEntity.getUserId());
                              integralRecordEntity.setSource("参与活动");
                              integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                              integralRecordService.update(integralRecordEntity);
                              allUserService.update(allUserEntity);
                          }
                      }
                  }else {
                      List<ActivityUserSignEntity> activityUserSignEntityList =  super.mapper.getListSignByAid(aid);
                      for (ActivityUserSignEntity activityUserSignEntity:activityUserSignEntityList) {
                              AllUserEntity allUserEntity =   allUserService.queryById(activityUserSignEntity.getUserId());
                              allUserEntity.setIntegral(allUserEntity.getIntegral()+activityEntity.getIntegral());
                              allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+activityEntity.getIntegral());
                              IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                              integralRecordEntity.setIntegral(activityEntity.getIntegral());
                              integralRecordEntity.setUserId(activityUserSignEntity.getUserId());
                              integralRecordEntity.setSource("参与活动");
                              integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                              integralRecordService.update(integralRecordEntity);
                              allUserService.update(allUserEntity);
                      }
                  }

            activityService.update(activityEntity);

    }

    /**
     * 项目结束自动给用户加积分
     * @param
     * @return
     */
   // @Transactional
    public void getIntegeral1(Long pid){
        ProjectEntity projectEntity =   projectService.queryById(pid);
        if(projectEntity.getSignIn().equals(1)){  //是否需要签到（1.是2.否）
            List<ProjectUserSignEntity> projectUserSignEntityList =  projectUserSignMapper.getProjectEntityByPid(projectEntity.getId());
            if(projectUserSignEntityList.size() != 0){
                for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
                    if(projectUserSignEntity.getStatus().equals(0)){
                     continue;
                    }else {
                        AllUserEntity allUserEntity = allUserService.queryById(projectUserSignEntity.getUserId());
                        if(allUserEntity != null){
                            allUserEntity.setIntegral(allUserEntity.getIntegral()+projectEntity.getIntegral());
                            allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+projectEntity.getIntegral());
                            IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                            integralRecordEntity.setIntegral(projectEntity.getIntegral());
                            integralRecordEntity.setUserId(projectUserSignEntity.getUserId());
                            integralRecordEntity.setSource("参与项目");
                            integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                            integralRecordService.update(integralRecordEntity);
                            allUserService.update(allUserEntity);
                        }

                    }

                }
            }
        }else {
            List<ProjectUserSignEntity> projectUserSignEntityList =  projectUserSignMapper.getProjectEntityByPid(projectEntity.getId());
            if(projectUserSignEntityList.size() != 0){
                for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
                        AllUserEntity allUserEntity = allUserService.queryById(projectUserSignEntity.getUserId());
                        if(allUserEntity != null){
                            allUserEntity.setIntegral(allUserEntity.getIntegral()+projectEntity.getIntegral());
                            allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+projectEntity.getIntegral());
                            IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                            integralRecordEntity.setIntegral(projectEntity.getIntegral());
                            integralRecordEntity.setUserId(projectUserSignEntity.getUserId());
                            integralRecordEntity.setSource("参与项目");
                            integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                            integralRecordService.update(integralRecordEntity);
                            allUserService.update(allUserEntity);

                        }

                }
            }

        }

        projectService.update(projectEntity);
    }


    /**
     * 通过用户id查询用户所有参与报名的活动
     * @param userId
     * @return
     */
   public List<ActivityUserSignEntity> getEntityByUser(Long userId){

        return super.mapper.getEntityByUser(userId);
    }




}