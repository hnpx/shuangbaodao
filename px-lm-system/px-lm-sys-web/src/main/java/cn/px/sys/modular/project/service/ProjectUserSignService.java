package cn.px.sys.modular.project.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.activity.constant.SignIsOrNotEnum;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.vo.UserVo;
import cn.px.sys.modular.integral.constant.TypeEnum;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.mapper.ProjectUserSignMapper;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (ProjectUserSign)表服务实现类
 *
 * @author
 * @since 2020-09-04 09:06:28
 */
@Service("projectUserSignService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "projectUserSign")
public class ProjectUserSignService extends BaseServiceImpl<ProjectUserSignEntity, ProjectUserSignMapper> {

    @Resource
    private ProjectService projectService;
    @Resource
    private AllUserService allUserService;
    @Resource
    private IntegralRecordService integralRecordService;

    /**
     * 根据项目id查询此项目报名人数
     * @param projectId
     * @return
     */
    public List<ProjectUserSignEntity> getProjectUserSignEntityList(@Param("projectId") Long projectId){
        return super.mapper.getProjectUserSignEntityList(projectId);
    }

    /**
     * 根据项目id查询此项目报名人
     * @param projectId
     * @return
     */
    public List<UserVo> getUserVo(@Param("projectId") Long projectId){
        return super.mapper.getUserVo(projectId);
    }


    /**
     * 查询项目报名
     * @param page
     * @param projectId
     * @return
     */
    public Page<Map<String,Object>> getPageList(Integer page,Integer pageSize,Long projectId,String unit,String name,String phone,Integer type,Integer photo){
        Page page1;
        if(pageSize != null){
             page1 = new Page(page,pageSize);
        }else {
            page1 = LayuiPageFactory.defaultPage();
        }

        return super.mapper.getPageList(page1,projectId,unit,name,phone,type,photo);
    }

    /**
     * 查询项目报名
     * @param page
     * @param projectId
     * @return
     */
    public Page<Map<String,Object>> getPageList1(Integer page,Integer pageSize,Long projectId,Long unit,String name,String phone){
        Page page1;
        if(pageSize != null){
            page1 = new Page(page,pageSize);
        }else {
            page1 = LayuiPageFactory.defaultPage();
        }

        return super.mapper.getPageList1(page1,projectId,unit,name,phone);
    }

    /**
     * 我认领的项目列表
     * @param
     * @param userId
     * @return
     */
    public Page<Map<String,Object>> getListByUserId(Integer page,Integer pageSize,Long userId,Integer status){
        Page page1 = new Page(page,pageSize);
        return super.mapper.getListByUserId(page1,userId,new Date(),status);
    }

    /**
     * 项目结束自动给用户加积分
     * @param
     * @return
     */
    @Transactional
    public void getIntegeral(){
        List<ProjectEntity> projectEntityList =  projectService.getList();
        for (ProjectEntity projectEntity:projectEntityList) {
            List<ProjectUserSignEntity> projectUserSignEntityList =  super.mapper.getProjectEntityByPid(projectEntity.getId());
            if(projectUserSignEntityList.size() != 0){
                for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
                    AllUserEntity allUserEntity = allUserService.queryById(projectUserSignEntity.getUserId());
                    allUserEntity.setIntegral(allUserEntity.getIntegral()+projectEntity.getIntegral());
                    allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+projectEntity.getIntegral());
                    IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                    integralRecordEntity.setIntegral(projectEntity.getIntegral());
                    integralRecordEntity.setUserId(projectUserSignEntity.getUserId());
                    integralRecordEntity.setSource("参与活动");
                    integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                    integralRecordService.update(integralRecordEntity);
                    allUserService.update(allUserEntity);
                }
            }
            projectEntity.setSignIn(SignIsOrNotEnum.IS_SIGN_ENUM_TRUE.getValue());
            projectService.update(projectEntity);
        }

    }

    /**
     * 项目结束自动给用户加积分
     * @param
     * @return
     */
    @Transactional
    public void getIntegeral1(Long pid){
            ProjectEntity projectEntity =   projectService.queryById(pid);
            List<ProjectUserSignEntity> projectUserSignEntityList =  super.mapper.getProjectEntityByPid(projectEntity.getId());
            if(projectUserSignEntityList.size() != 0){
                for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
                    AllUserEntity allUserEntity = allUserService.queryById(projectUserSignEntity.getUserId());
                    allUserEntity.setIntegral(allUserEntity.getIntegral()+projectEntity.getIntegral());
                    allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+projectEntity.getIntegral());
                    IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
                    integralRecordEntity.setIntegral(projectEntity.getIntegral());
                    integralRecordEntity.setUserId(projectUserSignEntity.getUserId());
                    integralRecordEntity.setSource("参与活动");
                    integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
                    integralRecordService.update(integralRecordEntity);
                    allUserService.update(allUserEntity);
                }
            }
            projectService.update(projectEntity);
        }

    /**
     * 表现档案
     * @param
     * @param userId
     * @return
     */
  public Page<Map<String,Object>> getPerformance(Long userId,String projectName,Long pid,Integer year){
        Page page = LayuiPageFactory.defaultPage();

        return super.mapper.getPerformance(page,userId,projectName,pid,year);
    }

    public void updateEnable(ProjectUserSignEntity entity){
      super.mapper.updateEnbale(entity);
    }


    /**
     * 通过用户id查询所有项目报名信息
     * @param userId
     * @return
     */
   public List<ProjectUserSignEntity> getEntityByUser( Long userId){
        return super.mapper.getEntityByUser(userId);
    }


    /**
     * 通过项目id查询所有的报名信息
     * @param pid
     * @return
     */
    public List<ProjectUserSignEntity> getEntityByPid(Long pid){

        return super.mapper.getEntityByPid(pid);
    }

    /**
     * 通过项目id和未认证未上传图片查询所有的报名信息
     * @param pid
     * @return
     */
    public List<ProjectUserSignEntity> getProjectUserSignEntityByPid(Long pid){
        return super.mapper.getProjectUserSignEntityByPid(pid);
    }
}