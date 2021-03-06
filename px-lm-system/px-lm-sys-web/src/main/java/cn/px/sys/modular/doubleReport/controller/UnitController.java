package cn.px.sys.modular.doubleReport.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.entity.ActiveCommentEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.service.ActiveCommentService;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import cn.px.sys.modular.doubleReport.vo.UnitVo;
import cn.px.sys.modular.doubleReport.wrapper.UnitWrapper;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integralWater.entity.IntegralWaterEntity;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.project.entity.ProjectCommentEntity;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectCommentService;
import cn.px.sys.modular.project.service.ProjectEvaluateService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ????????????(Unit)????????????
 *
 * @author
 * @since 2020-08-28 16:16:30
 */
@RestController
@RequestMapping("unit")
@Api(value = "????????????(Unit)??????")
public class UnitController extends BaseController<UnitEntity, UnitService> {

    private static final String PREFIX = "/modular/unit";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private CommunityClassService communityClassService;

    @Resource
    private UnitWrapper unitWrapper;

    @Autowired
    private AllUserService allUserService;
    @Autowired
    private ReportCadreService reportCadreService;
    @Resource
    private ProjectUserSignService projectUserSignService;
    @Resource
    private ActivityUserSignService activityUserSignService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private IntegralWaterService integralWaterService;
    @Resource
    private ProjectEvaluateService projectEvaluateService;
    @Resource
    private ProjectCommentService projectCommentService;
    @Resource
    private ActiveCommentService activeCommentService;
    @Resource
    private MemberService memberService;




    /**
     * ??????????????????????????????
     *
     * @param id ??????
     * @return ????????????
     */
    @GetMapping("/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
        return super.setSuccessModelMap(modelMap, super.service.queryById(id));
    }

    @ApiOperation("??????")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, UnitEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("??????")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, UnitEntity params) {
        //TODO ????????????
        if(params.getId() == null){
            return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        }else {
          UnitEntity unitEntity = super.service.queryById(params.getId());
          if(unitEntity != null){
              if(!unitEntity.getBelongingCommunity().equals(params.getBelongingCommunity())){
                  //??????????????????????????????????????????
                  ThreadUtil.execute(() -> {
                    List<ReportCadreEntity> reportCadreEntityList = reportCadreService.getReportCadreEntityByunit(params.getId());
                      reportCadreEntityList.forEach(reportCadreEntity -> {
                          reportCadreEntity.setBelongingCommunity(params.getBelongingCommunity());
                      });
                      reportCadreService.updateBatch(reportCadreEntityList);

                      List<MemberEntity> memberEntityList =  memberService.getMemberEntityByUnit(params.getId());
                      memberEntityList.forEach(memberEntity -> {
                          memberEntity.setBelongingCommunity(params.getBelongingCommunity());
                      });
                      memberService.updateBatch(memberEntityList);
                  });
                  return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());

              }else {
                  return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
              }
          }
            return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        }

    }

    @ApiOperation("??????")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, UnitEntity params) {
        Assert.notNull(params.getId(), "ID");

      UnitEntity unitEntity =  super.service.queryById(params.getId());
        //????????????????????????
        List<ProjectUserSignEntity> projectUserSignEntityList = projectUserSignService.getEntityByUser(unitEntity.getUserId());
        for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
            projectUserSignService.delete(projectUserSignEntity.getId());
        }
        //??????????????????
        List<ActivityUserSignEntity> activityUserSignEntityList =  activityUserSignService.getEntityByUser(unitEntity.getUserId());
        for (ActivityUserSignEntity activityUserSignEntity:activityUserSignEntityList) {
            activityUserSignService.delete(activityUserSignEntity.getId());
        }
        //????????????
        List<IntegralRecordEntity> integralRecordEntityList = integralRecordService.getEntityByUser(unitEntity.getUserId());
        for (IntegralRecordEntity integralRecordEntity:integralRecordEntityList) {
            integralRecordService.delete(integralRecordEntity.getId());
        }
        //??????????????????
        List<ActiveCommentEntity> activeCommentEntityList = activeCommentService.getListByUser(unitEntity.getUserId());
        for (ActiveCommentEntity activeCommentEntity:activeCommentEntityList) {
            activeCommentService.delete(activeCommentEntity.getId());
        }
        //??????????????????
        List<ProjectCommentEntity> projectCommentEntityList = projectCommentService.getListByUser(unitEntity.getUserId());
        for (ProjectCommentEntity projectCommentEntity:projectCommentEntityList) {
            projectCommentService.delete(projectCommentEntity.getId());
        }
        //????????????
        List<IntegralWaterEntity> integralWaterEntityList = integralWaterService.getListByUser(unitEntity.getUserId());
        for (IntegralWaterEntity integralWaterEntity:integralWaterEntityList) {
            integralWaterService.delete(integralWaterEntity.getId());
        }
        //????????????
        List<ProjectEvaluateEntity> projectEvaluateEntityList = projectEvaluateService.getListByUser(unitEntity.getUserId());
        for (ProjectEvaluateEntity projectEvaluateEntity:projectEvaluateEntityList) {
            projectEvaluateService.delete(projectEvaluateEntity.getId());
        }
        allUserService.delete(unitEntity.getUserId());
        reportCadreService.delete(params.getId());
        //return super.setSuccessModelMap(super.);
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("??????excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, UnitEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<UnitEntity> p = super.service.query(params, page);
        List<UnitEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("????????????.xls", "Unit"), UnitEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("????????????id??????????????????")
    @PutMapping("/read/listByBelongingCommunity")
    @ResponseBody
    public Object query(@RequestParam(required = false) Long belongingCommunity) {
     return super.setSuccessModelMap(super.service.getUnitEntityByCommunity(belongingCommunity));
    }

    @ApiOperation("????????????")
    @PutMapping("/read/homeList")
    @ResponseBody
    public Object query(@RequestParam(required = false) String name,@RequestParam(required = false) String contactPerson,
                        @RequestParam(required = false) Long belongingCommunity,@RequestParam(required = false) Integer isUnit) {
      Long cid = null;
      CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(LoginContextHolder.getContext().getUserId());
      if(communityClassEntity != null){
          cid = communityClassEntity.getId();
      }
      Page<Map<String,Object>> unit =  super.service.getList(name,cid,contactPerson,belongingCommunity,isUnit);
      Page<UnitListVo>  pv = unitWrapper.getVoPage(unit);
      return super.setSuccessModelMap(pv);
    }

    @ApiOperation("???????????????????????????????????????")
    @PutMapping("/read/unit")
    @ResponseBody
    public Object query1(@RequestParam("phone") String phone) {
      UnitEntity unitEntity = super.service.getUnitEntityByPhone(phone);
      if(unitEntity == null){
         return super.setModelMap("400","???????????????????????????");
      }else {
          UnitVo unitVo = new UnitVo();
          BeanUtil.copyProperties(unitEntity,unitVo);
          CommunityClassEntity communityClassEntity =  communityClassService.queryById(unitEntity.getBelongingCommunity());
          unitVo.setBelongingCommunityName(communityClassEntity.getName());
          return super.setSuccessModelMap(unitVo);
      }

    }

    @RequestMapping("/list")
    public Object list(){
        Map map = new HashMap();
        map.put("enable",1);
        return setSuccessModelMap(service.queryList(map));
    }
    @ApiOperation("????????????")
    @GetMapping("/read/unit/list")
    @ResponseBody
    public Object getList(){
        Long cid = null;
        CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(LoginContextHolder.getContext().getUserId());
        if(communityClassEntity != null){
            cid = communityClassEntity.getId();
        }
        List<UnitVo> unitVoList =  service.getUnitEntityByCommunity(cid);
        return setSuccessModelMap(unitVoList);
    }

    @ApiOperation("????????????")
    @GetMapping("/read/activity/list")
    @ResponseBody
    public Object query1(@RequestParam("belongingCommunity") Long belongingCommunity) {
        Long cid = null;
        CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(LoginContextHolder.getContext().getUserId());
        if(communityClassEntity != null){
            cid = communityClassEntity.getId();
        }
        List<UnitEntity> unitList =  super.service.getUnitEntityByCommunity1(cid);
        return super.setSuccessModelMap(unitList);
    }

}