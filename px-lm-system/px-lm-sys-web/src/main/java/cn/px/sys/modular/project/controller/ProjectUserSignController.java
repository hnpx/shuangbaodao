package cn.px.sys.modular.project.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.ExcelUtil.ExcelBaseUtil;
import cn.px.sys.modular.activity.wrapper.UserWrapper;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.integral.constant.TypeEnum;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integralWater.entity.IntegralWaterEntity;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.project.constant.PersonnelTypeEnum;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectEvaluateService;
import cn.px.sys.modular.project.service.ProjectService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.project.vo.ProjectUserSignVo;
import cn.px.sys.modular.system.vo.UserVO;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.sys.modular.wx.vo.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * (ProjectUserSign)????????????
 *
 * @author
 * @since 2020-09-04 09:06:28
 */
@RestController
@RequestMapping("projectUserSign")
@Api(value = "(ProjectUserSign)??????")
public class ProjectUserSignController extends BaseController<ProjectUserSignEntity, ProjectUserSignService> {

    private static final String PREFIX = "/modular/projectUserSign";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private UserWrapper userWrapper;
    @Resource
    private ProjectService projectService;
    @Resource
    private AllUserService allUserService;
    @Resource
    private UnitService unitService;
    @Resource
    private ProjectEvaluateService projectEvaluateService;
    @Resource
    private CommunityClassService communityClassService;


    @Autowired
    private MemberService memberService;


    @Autowired
    private ReportCadreService reportCadreService;
    @Autowired
    private IntegralWaterService integralWaterService;
    @Autowired
    private IntegralRecordService integralRecordService;
    @Autowired
    private ProjectUserSignService projectUserSignService;


    /**
     * ??????????????????????????????
     *
     * @param id ??????
     * @return ????????????
     */
    @GetMapping("/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
        ProjectUserSignEntity projectUserSignEntity = super.service.queryById(id);
        ProjectEntity project = projectService.queryById(projectUserSignEntity.getProjectId());
        UserVo userVo = allUserService.getName(projectUserSignEntity.getUserId());
        projectUserSignEntity.setProjectName(project.getName());
        projectUserSignEntity.setUserName(userVo.getName());
        if(projectUserSignEntity.getStatus() == null ||projectUserSignEntity.getStatus() == 0 ){
            projectUserSignEntity.setStatus(0);
        }
        return super.setSuccessModelMap(modelMap, projectUserSignEntity);
    }

    @ApiOperation("??????")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ProjectUserSignEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("??????")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ProjectUserSignEntity params) {

        //TODO ????????????
        ProjectEntity projectEntity = projectService.queryById(params.getProjectId());
       /* if(new Date().compareTo(projectEntity.getEndTime())==1 ){
            return super.setModelMap("400", "????????????????????????????????????");
        }*/
        if (projectEntity.getPersonnelType().equals(PersonnelTypeEnum.SIGN_ENUM_THREE.getValue())) {
            Long userId = LoginContextHolder.getContext().getUserId();
            AllUserEntity allUserEntity = allUserService.queryById(userId);
            if (allUserEntity.getType().equals(PersonnelTypeEnum.SIGN_ENUM_THREE.getValue())) {
                UnitEntity unitEntity = unitService.getUnitEntityByPhone(allUserEntity.getPhone());
                if (unitEntity.getId().equals(projectEntity.getBelongingUnit())) {
                    params.setUserId(LoginContextHolder.getContext().getUserId());
                    return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
                } else {
                    return super.setModelMap("400", "?????????????????????????????????????????????");
                }
            } else {
                return super.setModelMap("400", "???????????????????????????????????????");
            }
        }else if(projectEntity.getPersonnelType().equals(PersonnelTypeEnum.SIGN_ENUM_TWO.getValue())) {
            if(StringUtils.isNotEmpty(projectEntity.getUnits())){
                Long userId = LoginContextHolder.getContext().getUserId();
                AllUserEntity allUserEntity = allUserService.queryById(userId);
                if(allUserEntity.getType().equals(PersonnelTypeEnum.SIGN_ENUM_TWO.getValue())){
                    ReportCadreEntity reportCadreEntity = new ReportCadreEntity();
                    reportCadreEntity.setUserId(allUserEntity.getId());
                   ReportCadreEntity reportCadreEntity1 = reportCadreService.selectOne(reportCadreEntity);
                   if(reportCadreEntity1 != null){
                       Long unit = reportCadreEntity1.getBelongingUnit();
                      Boolean b =  projectEntity.getUnits().contains(unit.toString());
                      if(!b){
                          return super.setModelMap("400", "???????????????????????????????????????????????????????????????");
                      }
                   }
                }else {
                    return super.setModelMap("400", "?????????????????????????????????????????????");
                }

            }

        }
            Integer i = projectEntity.getCurrentPerson();
            if (i == null) {
                i = 0;
            }
            if (i < projectEntity.getMaxPerson()) {
                params.setUserId(LoginContextHolder.getContext().getUserId());
                ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
                projectUserSignEntity.setProjectId(params.getProjectId());
                projectUserSignEntity.setUserId(LoginContextHolder.getContext().getUserId());
                ProjectUserSignEntity p = super.service.selectOne(projectUserSignEntity);
                if (p == null) {
                    Integer currentPerson = i + 1;
                    projectEntity.setCurrentPerson(currentPerson);
                    projectService.update(projectEntity);
                    return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
                } else {
                    return super.setModelMap("400", "??????????????????????????????");
                }
            } else {
                return super.setModelMap("400", "?????????????????????????????????");
            }

    }


    @ApiOperation("????????????")
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(ModelMap modelMap, ProjectUserSignEntity params) {

        //TODO ????????????
        ProjectEntity projectEntity = projectService.queryById(params.getProjectId());
       /* if(new Date().compareTo(projectEntity.getEndTime())==1 ){
            return super.setModelMap("400", "????????????????????????????????????");
        }*/
        if (projectEntity.getPersonnelType().equals(PersonnelTypeEnum.SIGN_ENUM_THREE.getValue())) {
            Long userId = params.getUserId();
            AllUserEntity allUserEntity = allUserService.queryById(userId);
            if (allUserEntity.getType().equals(PersonnelTypeEnum.SIGN_ENUM_THREE.getValue())) {
                UnitEntity unitEntity = unitService.getUnitEntityByPhone(allUserEntity.getPhone());
                if (unitEntity.getId().equals(projectEntity.getBelongingUnit())) {
                    params.setUserId(params.getUserId());
                    return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
                } else {
                    return super.setModelMap("400", "?????????????????????????????????????????????");
                }
            } else {
                return super.setModelMap("400", "???????????????????????????????????????");
            }
        } else {
            Integer i = projectEntity.getCurrentPerson();
            if (i == null) {
                i = 0;
            }
            if (i < projectEntity.getMaxPerson()) {
                params.setUserId(params.getUserId());
                ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
                projectUserSignEntity.setProjectId(params.getProjectId());
                projectUserSignEntity.setUserId(params.getUserId());
                ProjectUserSignEntity p = super.service.selectOne(projectUserSignEntity);
                if (p == null) {
                    Integer currentPerson = i + 1;
                    projectEntity.setCurrentPerson(currentPerson);
                    projectService.update(projectEntity);
                    return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
                } else {
                    return super.setModelMap("400", "??????????????????????????????");
                }
            } else {
                return super.setModelMap("400", "?????????????????????????????????");
            }
        }

    }

    @ApiOperation("??????")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ProjectUserSignEntity params) {
        Assert.notNull(params.getId(), "ID");
        ProjectUserSignEntity ps = new ProjectUserSignEntity();
        ps.setId(params.getId());
        service.deleteByEntity(ps);

//        super.del(modelMap, params, LoginContextHolder.getContext().getUserId())
        return setSuccessModelMap();
    }

    @ApiOperation("??????excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ProjectUserSignEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ProjectUserSignEntity> p = super.service.query(params, page);
        List<ProjectUserSignEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "ProjectUserSign"), ProjectUserSignEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    /**
     *
     * @param page
     * @param pageSize
     * @param projectId
     * @param unit
     * @param name
     * @param phone
     * @param type ???????????????1.?????????2.????????????
     * @param photo ????????????????????????1.?????????2.????????????
     * @return
     */
    @ApiOperation("??????????????????")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize,
                        @RequestParam(required = false) Long projectId, @RequestParam(required = false) String unit, @RequestParam(required = false) String name,
                        @RequestParam(required = false) String phone,
                        @RequestParam(required = false) Integer type,
                        @RequestParam(required = false) Integer photo) {

        Page<Map<String, Object>> user = super.service.getPageList(page, pageSize, projectId, unit, name, phone, type,photo);
        Page<cn.px.sys.modular.activity.vo.UserVo> pv = userWrapper.getVoPage(user);
        return super.setSuccessModelMap(pv);

    }


    @ApiOperation("?????????????????????")
    @PostMapping("/upload/picture")
    @ResponseBody
    public Object getupload( @RequestParam("pid") Long pid,@RequestParam("picture") String picture){
     List<ProjectUserSignEntity> projectUserSignEntityList = super.service.getProjectUserSignEntityByPid(pid);
     if(projectUserSignEntityList.size()>0){
         projectUserSignEntityList.forEach(projectUserSignEntity -> {
             projectUserSignEntity.setPicture(picture);
             super.service.update(projectUserSignEntity);
         });
         return super.setSuccessModelMap();
     }else {
         return super.setModelMap("400","?????????????????????????????????");
     }

    }


    @ApiOperation("????????????")
    @PutMapping("/read/listMySelf")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer status) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String, Object>> project = super.service.getListByUserId(page, pageSize, userId, status);
        return super.setSuccessModelMap(project);

    }

    /* @Scheduled(cron = "0 0 0 * * ?")*/
    @ApiOperation("??????????????????????????????????????????")
    @PostMapping("/read/integeral")
    @ResponseBody
    public Object query1() {
        super.service.getIntegeral();
        return super.setSuccessModelMap();
    }


    @ApiOperation("????????????")
    @PutMapping("/read/erformance")
    public Object query(@RequestParam(required = false) Long userId, @RequestParam(required = false) String projectName) {
        Page<Map<String, Object>> ob = super.service.getPerformance(userId, projectName, null,null);

        ob.getRecords().forEach(a -> {
            Long pid = Long.parseLong(a.get("projectId").toString());
            int n = projectEvaluateService.getCount(pid, userId);
            if (n > 0) {
                a.put("type", 0);  //??????????????????
            } else {
                a.put("type", 1);   //????????????
            }
            if ("1".equals(a.get("enable").toString())) {
                ProjectEvaluateEntity projectEvaluateEntity = projectEvaluateService.getByPid(pid, userId);
                if (projectEvaluateEntity == null) {
                    a.put("reportPoints", "");
                } else {
                    a.put("reportPoints", projectEvaluateEntity.getReportPoints());
                }

            }


        });

        return super.setSuccessModelMap(ob);

    }

    @ApiOperation("????????????,?????????")
    @PutMapping("/read/erformance1")
    public Object query1(@RequestParam(required = false) Long userId, @RequestParam(required = false) String projectName,
                         @RequestParam("pid") Long pid,@RequestParam(required = false) Integer year) {
        Page<Map<String, Object>> ob = super.service.getPerformance(userId, projectName, pid,year);
        ob.getRecords().forEach(a -> {
            Long pid1 = Long.parseLong(a.get("projectId").toString());
            int n = projectEvaluateService.getCount(pid1, userId);
            if (n > 0) {
                a.put("type", 0);  //??????????????????
            } else {
                a.put("type", 1);   //????????????
            }
            if ("1".equals(a.get("enable").toString())) {
                ProjectEvaluateEntity projectEvaluateEntity = projectEvaluateService.getByPid(pid1, userId);
                if (projectEvaluateEntity == null) {
                    a.put("reportPoints", "");
                } else {
                    a.put("reportPoints", projectEvaluateEntity.getReportPoints());
                }

            }
            ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
            projectUserSignEntity.setUserId(userId);
            projectUserSignEntity.setProjectId(pid1);
            ProjectUserSignEntity projectUserSignEntity1 =  projectUserSignService.selectOne(projectUserSignEntity);
            if (projectUserSignEntity1 != null){
                a.put("picture",projectUserSignEntity1.getPicture());
            }

        });

        return super.setSuccessModelMap(ob);

    }


    @ApiOperation("????????????????????????")
    @DeleteMapping("/delect/archives")
    @ResponseBody
    public Object delete(@RequestParam("id") Long id) {
        ProjectUserSignEntity projectUserSignEntity = service.queryById(id);
        Long userId = projectUserSignEntity.getUserId();
        Long projectId = projectUserSignEntity.getProjectId();
        if (projectUserSignEntity.getEnable() == 2) {

            ProjectEvaluateEntity projectEvaluateEntity = projectEvaluateService.getByPid(projectUserSignEntity.getProjectId(), projectUserSignEntity.getUserId());
            if (projectEvaluateEntity != null) {
                //?????????????????????????????????
                AllUserEntity allUserEntity1 = allUserService.getAllUserEntityById(projectEvaluateEntity.getUserId());
                AllUserEntity allUserEntity = new AllUserEntity();
                allUserEntity.setRemainingPoints(allUserEntity1.getRemainingPoints() - projectEvaluateEntity.getCharityPoints());
                allUserEntity.setIntegral(allUserEntity1.getRemainingPoints() - projectEvaluateEntity.getCharityPoints());
                allUserEntity.setId(projectEvaluateEntity.getUserId());
                allUserService.update(allUserEntity);
                //??????????????????
                projectEvaluateService.delete(projectEvaluateEntity.getId());

            }
            //????????????????????????
            ProjectEntity projectEntity = projectService.queryById(projectId);
            if (projectEntity != null) {

                    IntegralRecordEntity integralRecordEntity = integralRecordService.getIntegralRecordByUserSource(userId, projectEntity.getName(),projectEntity.getIntegral());
                    if (integralRecordEntity != null) {
                        integralRecordService.delete(integralRecordEntity.getId());

                    }
                    //????????????????????????
                    IntegralWaterEntity integralWaterEntity = integralWaterService.getIntegralWaterByUserScore(userId, projectEntity.getName(),projectEntity.getReportPoints());
                    if (integralWaterEntity != null) {
                        integralWaterService.delete(integralWaterEntity.getId());
                    }

            }

            //??????????????????
            service.delete(id);
            //??????????????????
            projectService.delete(projectUserSignEntity.getProjectId());

        } else {
            return super.setModelMap("400", "????????????????????????????????????????????????");
        }

        return super.setSuccessModelMap();
    }


    @ApiOperation("??????????????????")
    @GetMapping("/excel/record")
    @ResponseBody
    public void excel(@RequestParam("userId") Long userId,@RequestParam("year") Integer year, HttpServletResponse response, HttpServletRequest request) {

        Page<Map<String, Object>> ob = super.service.getPerformance(userId, null, null,year);
        List<ProjectUserSignVo> projectUserSignVoList = new ArrayList<>();
        ob.getRecords().forEach(a -> {
            ProjectUserSignVo projectUserSignVo = new ProjectUserSignVo();
            projectUserSignVo.setCreateTime(a.get("createTime").toString().substring(0, 10));
            projectUserSignVo.setName(a.get("projectName").toString());
            try{
                projectUserSignVo.setAddress(a.get("address").toString());

            }catch (Exception e){
                projectUserSignVo.setAddress("");
            }
            try{
                projectUserSignVo.setServiceTime(a.get("serviceTime").toString());
            }catch (Exception e)
            {
                projectUserSignVo.setServiceTime("");
            }

            ProjectEvaluateEntity projectEvaluateEntity = projectEvaluateService.getByPid(Long.parseLong(a.get("projectId").toString()), userId);
            if (projectEvaluateEntity == null) {
                projectUserSignVo.setIntegral(0);
            } else {
                projectUserSignVo.setIntegral(projectEvaluateEntity.getReportPoints());
            }
            projectUserSignVo.setSign("");
            projectUserSignVoList.add(projectUserSignVo);
        });
        //Collections.reverse(projectUserSignVoList);
        String name = "???????????????";
        ExcelBaseUtil.exportExcel(projectUserSignVoList, name, "???????????????", ProjectUserSignVo.class, "???????????????.xls", response);
    }


    @ApiOperation("???????????????")
    @PostMapping("/update/picture")
    @ResponseBody
    public Object getUploadPicture(ProjectUserSignEntity param) {
        ProjectUserSignEntity projectUserSignEntity = projectUserSignService.queryById(param.getId());
        projectUserSignEntity.setPicture(param.getPicture());
        projectUserSignService.update(projectUserSignEntity);
        return super.setSuccessModelMap();
    }

    @ApiOperation("???????????????,????????????")
    @GetMapping("/integral/certification")
    @ResponseBody
    public Object getIntegral(@RequestParam("pid") Long pid,Integer integral) {
        ProjectEntity projectEntity =    projectService.queryById(pid);
        List<ProjectUserSignEntity> projectUserSignEntityList = projectUserSignService.getEntityByPid(pid);
        projectUserSignEntityList.forEach(projectUserSignEntity -> {
            ProjectEvaluateEntity projectEvaluateEntity = projectEvaluateService.getByPid(projectUserSignEntity.getProjectId(), projectUserSignEntity.getUserId());
            if(projectEvaluateEntity == null){
                //????????????
                ProjectEvaluateEntity projectEvaluateEntity3 = new ProjectEvaluateEntity();
                projectEvaluateEntity3.setProjectId(projectUserSignEntity.getProjectId());
                projectEvaluateEntity3.setUserId(projectUserSignEntity.getUserId());
                ProjectEvaluateEntity projectEvaluateEntity2 =  projectEvaluateService.selectOne(projectEvaluateEntity3);
                if(projectEvaluateEntity2 == null){
                    ProjectEvaluateEntity projectEvaluateEntity1 = new ProjectEvaluateEntity();
                    projectEvaluateEntity1.setContent("?????????????????????????????????");
                    projectEvaluateEntity1.setProjectId(projectUserSignEntity.getProjectId());
                    projectEvaluateEntity1.setUserId(projectUserSignEntity.getUserId());
                    projectEvaluateEntity1.setReportPoints(integral);
                    projectEvaluateEntity1.setCreateTime(new Date());
                    projectEvaluateService.update(projectEvaluateEntity1);
                }else {
                    projectEvaluateEntity2.setContent("?????????????????????????????????");
                    projectEvaluateEntity2.setReportPoints(integral);
                    projectEvaluateEntity2.setUpdateTime(new Date());
                    projectEvaluateService.update(projectEvaluateEntity2);
                }

                //???????????????
                IntegralWaterEntity integralWaterEntity = new IntegralWaterEntity();
                integralWaterEntity.setIntegral(Double.valueOf(integral));
                integralWaterEntity.setUserId(projectUserSignEntity.getUserId());
                integralWaterEntity.setCreateTime(new Date());
                integralWaterEntity.setIntegralDesc(projectEntity.getName());
                integralWaterService.update(integralWaterEntity);

                //?????????????????????????????????(1.?????????2.?????????)
                projectUserSignEntity.setCertificationStatus(2);
                projectUserSignService.update(projectUserSignEntity);
            }
        });
        return super.setSuccessModelMap();
    }

}