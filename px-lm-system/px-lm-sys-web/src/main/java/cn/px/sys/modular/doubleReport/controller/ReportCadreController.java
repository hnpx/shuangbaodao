package cn.px.sys.modular.doubleReport.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
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
import cn.px.sys.modular.doubleReport.constant.IsBindEnum;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.vo.ReportCadreVo;
import cn.px.sys.modular.doubleReport.vo.UserVo;
import cn.px.sys.modular.doubleReport.wrapper.ReportCadreWrapper;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integralWater.entity.IntegralWaterEntity;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import cn.px.sys.modular.project.entity.ProjectCommentEntity;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectCommentService;
import cn.px.sys.modular.project.service.ProjectEvaluateService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.system.entity.User;
import cn.px.sys.modular.system.service.UserService;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 党员干部信息管理(ReportCadre)表控制层
 *
 * @author
 * @since 2020-08-28 14:10:46
 */
@RestController
@RequestMapping("reportCadre")
@Api(value = "党员干部信息管理(ReportCadre)管理")
public class ReportCadreController extends BaseController<ReportCadreEntity, ReportCadreService> {

    private static final String PREFIX = "/modular/reportCadre";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ReportCadreWrapper reportCadreWrapper;

    @Autowired
    private UserService userService;
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



    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
        return super.setSuccessModelMap(modelMap, super.service.queryById(id));
    }

    @ApiOperation("查询")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ReportCadreEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ReportCadreEntity params) {
        //TODO 数据验证
        if(super.service.countByPhone(params.getPhone()) == 0){
            return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        }else {
            return super.setModelMap("400","已存在此用户信息");
        }

    }

    @ApiOperation("修改")
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(ModelMap modelMap, ReportCadreEntity params) {
        //TODO 数据验证


        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ReportCadreEntity params) {
        Assert.notNull(params.getId(), "ID");
        ReportCadreEntity reportCadreEntity = super.service.queryById(params.getId());

        //删除项目报名记录
        List<ProjectUserSignEntity> projectUserSignEntityList = projectUserSignService.getEntityByUser(reportCadreEntity.getUserId());
        for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
            projectUserSignService.delete(projectUserSignEntity.getId());
        }
        //活动报名记录
        List<ActivityUserSignEntity> activityUserSignEntityList =  activityUserSignService.getEntityByUser(reportCadreEntity.getUserId());
        for (ActivityUserSignEntity activityUserSignEntity:activityUserSignEntityList) {
            activityUserSignService.delete(activityUserSignEntity.getId());
        }
        //积分记录
        List<IntegralRecordEntity> integralRecordEntityList = integralRecordService.getEntityByUser(reportCadreEntity.getUserId());
        for (IntegralRecordEntity integralRecordEntity:integralRecordEntityList) {
            integralRecordService.delete(integralRecordEntity.getId());
        }
        //活动评论记录
        List<ActiveCommentEntity> activeCommentEntityList = activeCommentService.getListByUser(reportCadreEntity.getUserId());
        for (ActiveCommentEntity activeCommentEntity:activeCommentEntityList) {
            activeCommentService.delete(activeCommentEntity.getId());
        }
        //项目评论记录
        List<ProjectCommentEntity> projectCommentEntityList = projectCommentService.getListByUser(reportCadreEntity.getUserId());
        for (ProjectCommentEntity projectCommentEntity:projectCommentEntityList) {
            projectCommentService.delete(projectCommentEntity.getId());
        }
        //虚拟积分
      List<IntegralWaterEntity> integralWaterEntityList = integralWaterService.getListByUser(reportCadreEntity.getUserId());
        for (IntegralWaterEntity integralWaterEntity:integralWaterEntityList) {
            integralWaterService.delete(integralWaterEntity.getId());
        }
        //认证项目
      List<ProjectEvaluateEntity> projectEvaluateEntityList = projectEvaluateService.getListByUser(reportCadreEntity.getUserId());
        for (ProjectEvaluateEntity projectEvaluateEntity:projectEvaluateEntityList) {
            projectEvaluateService.delete(projectEvaluateEntity.getId());
        }
        allUserService.delete(reportCadreEntity.getUserId());
        reportCadreService.delete(params.getId());
        return super.setSuccessModelMap();
    }

    @ApiOperation("导出excel")
    @RequestMapping("/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        Long  belongingCommunity = null;
        Long belongingUnit = null;
        if(StringUtils.isNotEmpty(request.getParameter("belongingCommunity"))){
            belongingCommunity = Long.parseLong(request.getParameter("belongingCommunity"));
        }
        if(StringUtils.isNotEmpty(request.getParameter("belongingUnit"))){
             belongingUnit = Long.parseLong(request.getParameter("belongingUnit"));
        }

        String year = request.getParameter("year");
        Workbook wb = super.service.createWorkbook(belongingCommunity, belongingUnit, year);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = "党员信息表";
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xls" );
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("导入excel")
    @PostMapping("/upload")
    public  @ResponseBody Object  uploadExcel(MultipartFile file) throws Exception {
        if(file.isEmpty()){
            return "文件不能为空";
        }
        List<ReportCadreEntity> entityList = super.service.getBankListByExcel(file);
        //插入数据
        for (ReportCadreEntity entity : entityList) {
            super.service.insert(entity);
        }
        return super.setSuccessModelMap();
    }


    @ApiOperation("修改党员干部是否绑定")
    @PostMapping("/update/changeIsBind")
    @ResponseBody
    public Object changeStatus(ModelMap modelMap,Long id) {
        ReportCadreEntity params = this.service.queryById(id);

        if(params.getIsBind().equals(IsBindEnum.IS_BIND_TRUE.getValue())){
            params.setIsBind(IsBindEnum.IS_BIND_FALSE.getValue());
        }else {
            params.setIsBind(IsBindEnum.IS_BIND_TRUE.getValue());
        }
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("绑定个人信息")
    @PostMapping("/update/isBind")
    @ResponseBody
    public Object update(@RequestBody UserVo userVo) {
        //TODO 数据验证
      int i =  super.service.getIsBind(userVo,LoginContextHolder.getContext().getUserId());
        if(i == 1){
            return super.setModelMap("400","党员干部信息不存在");
        }else if(i == 2){
            return super.setModelMap("400","单位信息不存在");
        }else if(i==3){
            return super.setModelMap("400","您的手机号在党员干部或单位中已存在，请谨慎绑定用户角色");
        }
        return super.setSuccessModelMap();
    }



    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) String name,@RequestParam(required = false) String phone,
                        @RequestParam(required = false) Long belongingCommunity,@RequestParam(required = false) Long belongingUnit,
                        @RequestParam(required = false) Long expertise,@RequestParam(required = false) String year) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> cadre =  super.service.getListHome(name,phone,userId,belongingCommunity,belongingUnit,expertise);
        cadre.getRecords().forEach(map -> {
            map.put("year",year);
        });
        Page<ReportCadreVo>  pv = reportCadreWrapper.getVoPage(cadre);
        return super.setSuccessModelMap(pv);

    }



    @ApiOperation("查询列表")
    @PutMapping("/read/queryList")
    @ResponseBody
    public Object queryList(@RequestParam(required = false) String name,@RequestParam(required = false) String phone,@RequestParam(required = false) Long belongingCommunity,@RequestParam(required = false) Long belongingUnit,@RequestParam(required = false) Long expertise) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page page = LayuiPageFactory.defaultPage();
        User user = userService.getById(userId);
        belongingUnit = user.getUnitId();
        Page<Map<String,Object>> cadre =  super.service.getListByUnitId(page,belongingUnit,name,phone);
        Page<ReportCadreVo>  pv = reportCadreWrapper.getVoPage(cadre);
        return super.setSuccessModelMap(pv);

    }

}