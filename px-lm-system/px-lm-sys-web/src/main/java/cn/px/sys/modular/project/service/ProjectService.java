package cn.px.sys.modular.project.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.activity.constant.IsSignEnum;
import cn.px.sys.modular.activity.constant.SignEnum;
import cn.px.sys.modular.activity.constant.TimeStatusEnum;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.vo.ActivityDeVo;
import cn.px.sys.modular.activity.vo.CommentVo;
import cn.px.sys.modular.activity.vo.UserVo;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.mapper.ProjectMapper;
import cn.px.sys.modular.project.vo.ProjectVo;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/*import com.px.msg.IMessageService;
import com.px.msg.MessageService;
import com.px.msg.vo.SendMessageParam;*/
import jodd.util.URLDecoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目管理(Project)表服务实现类
 *
 * @author
 * @since 2020-09-02 20:20:08
 */
@Service("projectService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "project")
public class ProjectService extends BaseServiceImpl<ProjectEntity, ProjectMapper> {

    @Resource
    private ProjectUserSignService projectUserSignService;
    @Resource
    private ProjectCommentService projectCommentService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private ProjectService projectService;
    @Resource
    private UnitService unitService;


    @Autowired
    private MemberService memberService;


    @Autowired
    private ReportCadreService reportCadreService;

    @Autowired
    private AllUserService allUserService;
    @Resource
    private ProjectEvaluateService projectEvaluateService;
  /*  @Resource
    private MessageService messageService;
*/
    /**
     * 查询项目列表
     * @param
     * @param name
     * @return
     */
   public Page<Map<String,Object>> getListPage( String name,Long cid,String contactPerson,String phone,Integer timeStatus,Integer status,Long companyClass){
        Page page = LayuiPageFactory.defaultPage();

        return super.mapper.getListPage(page,name,cid,contactPerson,phone,timeStatus,status,companyClass);
    }

    /**
     * 查询项目列表通过项目分类id
     * @param
     * @param
     * @return
     */
    public Page<Map<String,Object>> getListPage1(Integer page,Integer pageSize, Long cid,Integer timeType){
       Page page1 = new Page(page,pageSize);
       Date date = new Date();
       return super.mapper.getListPage1(page1,cid,date,timeType);
    }


    public ProjectVo getProjectVo(Long id, Long userId){

        ProjectEntity projectEntity =  super.mapper.selectById(id);
        UnitEntity unitEntity = new UnitEntity();
        if(projectEntity.getPersonnelType()==3){ //单位
             unitEntity = unitService.queryById(projectEntity.getBelongingUnit());
        }
        ProjectVo projectVo = new ProjectVo();
        BeanUtil.copyProperties(projectEntity,projectVo);
        try{
            projectVo.setContent(URLDecoder.decode(projectVo.getContent(),"UTF-8"));
        }catch (Exception e){

        }
        if(unitEntity!= null){
            projectVo.setUnitName(unitEntity.getName());
        }

        //判断项目开始和未开始状态
        if(projectVo.getEndTime().compareTo(new Date())==-1){
            projectVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_FALSE.getValue());
        }else{
            if(projectVo.getStartTime().compareTo(new Date())==1){
                projectVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_TRUE.getValue());
            }else {
                projectVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_FALSE.getValue());
            }
        }
        if (StringUtils.isNotEmpty(projectVo.getCertification())){
            projectVo.setTimeStatus(TimeStatusEnum.TIME_STATUS_END.getValue());
        }



        //判断此活动用户是否已经报名
        ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
        projectUserSignEntity.setProjectId(id);
        projectUserSignEntity.setUserId(userId);
        ProjectUserSignEntity projectUserSignEntity1 = projectUserSignService.selectOne(projectUserSignEntity);

        if(projectUserSignEntity1 == null){
            projectVo.setIsSign(IsSignEnum.IS_SIGN_ENUM_FALSE.getValue());
            projectVo.setIsCertification(2);  //不可以认证
        }else {
            projectVo.setIsSign(IsSignEnum.IS_SIGN_ENUM_TRUE.getValue());
            projectVo.setSignInOrOut(projectUserSignEntity1.getStatus());
            ProjectEvaluateEntity projectEvaluateEntity = projectEvaluateService.getByPid(id,userId);
            //判断项目是否签到 是否需要签到（1.是2.否
            if(projectEntity.getSignIn() == 1){
                if(projectUserSignEntity1.getStatus() != 0){
                    if(projectEvaluateEntity == null){
                        projectVo.setIsCertification(1);  //可以认证
                    }else {
                        projectVo.setIsCertification(2);  //不可以认证
                    }
                }else {
                    projectVo.setIsCertification(2);  //不可以认证
                }
            }else {
                if(projectEvaluateEntity == null){
                    projectVo.setIsCertification(1);  //可以认证
                }else {
                    projectVo.setIsCertification(2);  //不可以认证
                }
            }
        }
        List<UserVo> userVoList = projectUserSignService.getUserVo(id);


        userVoList.forEach(a->{


            Long uId =  a.getUserId();
            if (uId == null){
                return;
            }
            AllUserEntity allUserEntity =   allUserService.queryById(uId);
            if (allUserEntity == null){
                return;
            }
            Integer userType = allUserEntity.getType();
            if (userType == null){
                return;
            }
            //普通用户
            if (userType == 1){
                MemberEntity mEntity = new MemberEntity();
                mEntity.setUserId(uId);
                MemberEntity memberEntity = memberService.selectOne(mEntity);
                if (memberEntity==null){
                    return;
                }
                a.setRealName(memberEntity.getName());
                a.setPhone(memberEntity.getPhone());

            };

            //党员干部
            if (userType == 2) {
                ReportCadreEntity rEntity = new ReportCadreEntity();
                rEntity.setUserId(uId);
                ReportCadreEntity reportCadreEntity = reportCadreService.selectOne(rEntity);
                if(reportCadreEntity != null){
                    a.setRealName(reportCadreEntity.getName());
                    a.setPhone(reportCadreEntity.getPhone());
                }

            };

            //单位
            if (userType == 3) {
                UnitEntity uEntity = new UnitEntity();
                uEntity.setUserId(uId);
                UnitEntity unitEntity1 = unitService.selectOne(uEntity);
                if(unitEntity1 != null){
                    a.setRealName(unitEntity1.getName());
                    a.setPhone(unitEntity1.getPhone());
                }

            }

        });

        projectVo.setUserVoList(userVoList);
        List<CommentVo> commentVoList = projectCommentService.getCommentVoList(id);
        projectVo.setCommentVoList(commentVoList);
        return projectVo;
    }

    /**
     * 项目签到接口
     * @param pid 项目id
     * @return
     */
    public int getSignIn(Long pid,Long userId){

        ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
        projectUserSignEntity.setUserId(userId);
        projectUserSignEntity.setProjectId(pid);
        ProjectUserSignEntity projectUserSignEntity1 =  projectUserSignService.selectOne(projectUserSignEntity);
        if(projectUserSignEntity1 != null && projectUserSignEntity1.getStatus().equals(SignEnum.SIGN_ENUM_ONE.getValue())){
            projectUserSignEntity1.setStatus(SignEnum.SIGN_ENUM_TWO.getValue());
            ProjectUserSignEntity projectUserSignEntity2 = projectUserSignService.update(projectUserSignEntity1);
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 项目签出接口
     * @param pid 项目id
     * @return
     */
    @Transactional
    public int getSignOut(Long pid,Long userId){
       ProjectEntity projectEntity = projectService.queryById(pid);
        ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
        projectUserSignEntity.setUserId(userId);
        projectUserSignEntity.setProjectId(pid);
        ProjectUserSignEntity projectUserSignEntity1 =  projectUserSignService.selectOne(projectUserSignEntity);
        if(getTime(projectEntity.getEndTime())){
            if(projectUserSignEntity1 != null && projectUserSignEntity1.getStatus().equals(SignEnum.SIGN_ENUM_TWO.getValue()) ){
               // integralRecordService.getIntegralRecordEntity1(pid,userId);
                projectUserSignEntity1.setStatus(SignEnum.SIGN_ENUM_THREE.getValue());
                ProjectUserSignEntity projectUserSignEntity2 = projectUserSignService.update(projectUserSignEntity1);
                return 1;
            }else {
                return 0;
            }
        }else {
            return 3;
        }


    }

    /**
     * 判断签出是否再结束一个小时只内
     * @param date
     * @return
     */
    public Boolean getTime(Date date){
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.setTime(date);
        long lastly = c.getTimeInMillis();
        return (lastly - now) <= 3600000 & (lastly - now)>= 0;
    }

    /**
     * 项目没有开启签到  每天凌晨自动加积分
     * @return
     */
    public List<ProjectEntity> getList(){
        return super.mapper.getList();
    }


    public void insertEntity(ProjectEntity entity){
        super.mapper.insertEntity(entity);
    }

    /**
     * 项目清单列表
     * @param
     * @param classId
     * @return
     */
    Page<Map<String,Object>> getPageList(Integer page,Integer pageSize,Long classId){

        Page page1 = new Page(page,pageSize);
       return super.mapper.getPageList(page1,classId);
    }

    /**
     * 微信模板通知
     */
   /* public void get(){
        SendMessageParam msgParam1 = new SendMessageParam();
        msgParam1.setMethod(IMessageService.METHOD_WX);
        msgParam1.setKey("companyAcSignRelease");
        msgParam1.putData("thing1", "name", 20);
        msgParam1.putData("thing4","公司活动报名开始");
        msgParam1.putData("thing6","detail",20);
        msgParam1.putOtherData("id", "");
        msgParam1.addTo("");
        this.messageService.send(msgParam1);
    }*/
}