package cn.px.sys.modular.project.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.constant.SignEnum;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.project.vo.ProjectVo;
import cn.px.sys.modular.project.wrapper.ProjectWrapper;
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
 * 项目管理(Project)表控制层
 *
 * @author
 * @since 2020-09-02 20:20:08
 */
@RestController
@RequestMapping("api/project")
@Api(value = "项目管理(Project)管理")
public class ProjectApiController extends BaseController<ProjectEntity, ProjectService> {

    private static final String PREFIX = "/modular/project";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ProjectWrapper projectWrapper;
    @Resource
    private ProjectUserSignService projectUserSignService;

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
    public Object query(ModelMap modelMap, ProjectEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ProjectEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ProjectEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ProjectEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ProjectEntity> p = super.service.query(params, page);
        List<ProjectEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("项目管理.xls", "Project"), ProjectEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("查询列表通过项目分类id")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam("pageSize") Integer pageSize,@RequestParam(required = false) Long cid,@RequestParam(required = false) Integer timeType) {
      Page<Map<String,Object>> project = super.service.getListPage1(page,pageSize,cid,timeType);
      Page<ProjectVo> pv =  projectWrapper.getVoPage(project);
      return super.setSuccessModelMap(pv);
    }

    @ApiOperation("上传证件照")
    @GetMapping("/upload/picture")
    @ResponseBody
    public Object upload(@RequestParam("uid") Long uid,@RequestParam("picture") String picture,@RequestParam("pid") Long pid) {
        ProjectEntity projectEntity = super.service.queryById(pid);
        ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
        projectUserSignEntity.setUserId(uid);
        projectUserSignEntity.setProjectId(pid);
        ProjectUserSignEntity projectUserSignEntity1 = projectUserSignService.selectOne(projectUserSignEntity);
        if(projectEntity.getSignIn()==1){
            if(projectUserSignEntity1 != null){
                if(projectUserSignEntity1.getStatus().equals(SignEnum.SIGN_ENUM_ONE.getValue())){
                    return super.setModelMap("400","您还未签到不能进行上传认证照");
                }
                projectUserSignEntity1.setPicture(picture);
                projectUserSignService.update(projectUserSignEntity1);
                return super.setSuccessModelMap();
            }else{
                return super.setModelMap("400","您未认领不能上传认证照");
            }
        }else {
            projectUserSignEntity1.setPicture(picture);
            projectUserSignService.update(projectUserSignEntity1);
            return super.setSuccessModelMap();
        }

    }

    @ApiOperation("上传证件照回显")
    @GetMapping("/picture/echo")
    @ResponseBody
    public Object echo(@RequestParam("uid") Long uid,@RequestParam("pid") Long pid){
        ProjectUserSignEntity projectUserSignEntity = new ProjectUserSignEntity();
        projectUserSignEntity.setUserId(uid);
        projectUserSignEntity.setProjectId(pid);
        ProjectUserSignEntity projectUserSignEntity1 = projectUserSignService.selectOne(projectUserSignEntity);
        return super.setSuccessModelMap(projectUserSignEntity1);

    }

}