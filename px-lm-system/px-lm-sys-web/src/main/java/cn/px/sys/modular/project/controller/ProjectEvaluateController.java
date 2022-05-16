package cn.px.sys.modular.project.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.integral.constant.TypeEnum;
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
import cn.px.sys.modular.wx.vo.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (ProjectEvaluate)表控制层
 *
 * @author
 * @since 2020-09-28 11:03:23
 */
@RestController
@RequestMapping("projectEvaluate")
@Api(value = "(ProjectEvaluate)管理")
public class ProjectEvaluateController extends BaseController<ProjectEvaluateEntity, ProjectEvaluateService> {

    private static final String PREFIX = "/modular/projectEvaluate";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private AllUserService allUserService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectUserSignService signService;
    @Autowired
    private IntegralWaterService integralWaterService;
    @Autowired
    private IntegralRecordService integralRecordService;



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
    public Object query(ModelMap modelMap, ProjectEvaluateEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    @Transactional
    public Object update(ModelMap modelMap, ProjectEvaluateEntity params) {
        //TODO 数据验证
        ProjectEntity projectEntity =   projectService.queryById(params.getProjectId());
        if(projectEntity.getEnable()==3){ //虚拟项目
            projectEntity.setIntegral(params.getCharityPoints());
            projectEntity.setReportPoints(params.getReportPoints());
            projectService.update(projectEntity);
            //双报到积分积分流水
            IntegralWaterEntity integralWaterEntity = new IntegralWaterEntity();
            integralWaterEntity.setUserId(params.getUserId());
            integralWaterEntity.setIntegral(Double.valueOf(params.getReportPoints()));
            integralWaterEntity.setIntegralDesc(projectEntity.getName());
            integralWaterService.update(integralWaterEntity);
            //积分记录
            IntegralRecordEntity integralRecordEntity = new IntegralRecordEntity();
            integralRecordEntity.setType(TypeEnum.TYPE_ENUM_ONE.getValue());
            integralRecordEntity.setUserId(params.getUserId());
            integralRecordEntity.setSource(projectEntity.getName());
            integralRecordEntity.setIntegral(params.getCharityPoints());
            integralRecordService.update(integralRecordEntity);
           AllUserEntity allUserEntity = allUserService.queryById(params.getUserId());
           if(allUserEntity.getIntegral()==null){
               allUserEntity.setIntegral(params.getCharityPoints());
           }else {
               allUserEntity.setIntegral(allUserEntity.getIntegral()+params.getCharityPoints());
           }
           if(allUserEntity.getRemainingPoints()==null){
               allUserEntity.setRemainingPoints(params.getCharityPoints());
           }else {
               allUserEntity.setRemainingPoints(allUserEntity.getRemainingPoints()+params.getCharityPoints());
           }
           allUserService.update(allUserEntity);

        }else {
           /* projectEntity.setReportPoints(params.getReportPoints());
            projectService.update(projectEntity);*/
            //双报到积分积分流水
            IntegralWaterEntity integralWaterEntity = new IntegralWaterEntity();
            integralWaterEntity.setUserId(params.getUserId());
            integralWaterEntity.setIntegral(Double.valueOf(params.getReportPoints()));
            integralWaterEntity.setIntegralDesc(projectEntity.getName());
            integralWaterService.update(integralWaterEntity);
        }


        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ProjectEvaluateEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ProjectEvaluateEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ProjectEvaluateEntity> p = super.service.query(params, page);
        List<ProjectEvaluateEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "ProjectEvaluate"), ProjectEvaluateEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam("projectId") Long projectId) {
       Page<Map<String,Object>> pro = super.service.getHomeList(projectId);
       pro.getRecords().forEach(a->{
        UserVo userVo=  allUserService.getName(Long.parseLong(a.get("userId").toString()));
        a.put("userName",userVo.getName());
       /* ProjectEntity projectEntity = projectService.queryById(Long.parseLong(a.get("projectId").toString()));
        a.put("projectName",projectEntity.getName());*/
       });
       return super.setSuccessModelMap(pro);
    }
    @ApiOperation("根据项目id和用户id查询")
    @PostMapping("/read/getObject")
    @ResponseBody
    public Object getObject(@RequestParam("projectId") Long projectId,@RequestParam("userId") Long userId){

       return super.setSuccessModelMap(super.service.getByPid(projectId,userId));
    }

}