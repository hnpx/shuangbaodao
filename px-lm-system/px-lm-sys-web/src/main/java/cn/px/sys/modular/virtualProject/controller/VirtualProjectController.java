package cn.px.sys.modular.virtualProject.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.ExcelUtil.ExcelBaseUtil;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.demandClass.entity.DemandClassEntity;
import cn.px.sys.modular.demandClass.service.DemandClassService;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.system.entity.User;
import cn.px.sys.modular.system.service.UserService;
import cn.px.sys.modular.virtualProject.entity.VirtualProjectEntity;
import cn.px.sys.modular.virtualProject.service.VirtualProjectService;
import cn.px.sys.modular.virtualProject.vo.VirtualProjectExcelVo;
import cn.px.sys.modular.virtualProject.vo.VirtualProjectVo;
import cn.px.sys.modular.virtualProject.wrapper.VirtualProjectWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (VirtualProject)表控制层
 *
 * @author
 * @since 2020-12-16 09:38:31
 */
@RestController
@RequestMapping("virtualProject")
@Api(value = "(VirtualProject)管理")
public class VirtualProjectController extends BaseController<VirtualProjectEntity, VirtualProjectService> {

    private static final String PREFIX = "/modular/virtualProject";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private VirtualProjectWrapper virtualProjectWrapper;

    @Resource
    private CommunityClassService communityClassService;
    @Resource
    private UnitService unitService;
    @Resource
    private DemandClassService demandClassService;
    @Resource
    private UserService userService;


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
    public Object query(ModelMap modelMap, VirtualProjectEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, VirtualProjectEntity params) {
        //TODO 数据验证
        params.setStatus(1);//1.未完成
        Long userId = LoginContextHolder.getContext().getUserId();
        if(userId != 1){
            User user = userService.getById(userId);
            if(user.getRoleId().contains("1299516539338305538")){
                CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
                if(!params.getBelongingCommunity().equals(communityClassEntity.getId())){
                    return super.setModelMap("400","您所添加的活动计划不属于您属于的社区无法添加");
                }
            }
        }

        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, VirtualProjectEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, VirtualProjectEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<VirtualProjectEntity> p = super.service.query(params, page);
        List<VirtualProjectEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "VirtualProject"), VirtualProjectEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }



    @ApiOperation("查询列表")
    @PutMapping("/read/listPage")
    @ResponseBody
    public Object query1(@RequestParam(required = false) String name,@RequestParam(required = false) Long belongingCommunity,
                         @RequestParam(required = false) Long belongingUnit,@RequestParam(required = false) Integer status,
                         @RequestParam(required = false) Long companyClass) {

        Long userId = LoginContextHolder.getContext().getUserId();
        if(userId == 1){
            belongingCommunity = belongingCommunity;
        }else {
        User user = userService.getById(userId);
        if(user.getRoleId().contains("1299516539338305538")){
           CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
           if(belongingCommunity != null){
               if(!belongingCommunity.equals(communityClassEntity.getId())){
                   return super.setModelMap("200","您无法查看此社区的信息",null);
               }else {
                   belongingCommunity = communityClassEntity.getId();
               }
           }else {
               belongingCommunity = communityClassEntity.getId();
           }
        }
        }
        Page<Map<String,Object>> info = super.service.getList(name,belongingCommunity,belongingUnit,status,companyClass);
        Page<VirtualProjectVo> pv=   virtualProjectWrapper.getVoPage(info);
        return super.setSuccessModelMap(pv);
    }


    @ApiOperation("修改状态")
    @PostMapping("/update/status")
    @ResponseBody
    public Object update1(ModelMap modelMap, VirtualProjectEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }


    @ApiOperation("导出")
    @GetMapping("/read/excel")
    @ResponseBody
    public void getExcel(HttpServletResponse response, HttpServletRequest request) {

        Long belongingCommunity = null;
        Long userId = LoginContextHolder.getContext().getUserId();
        if(userId == 1){
            belongingCommunity = null;
        }else {
            User user = userService.getById(userId);
            if(user.getRoleId().contains("1299516539338305538")){
                CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
                belongingCommunity = communityClassEntity.getId();
            }
        }
      List<VirtualProjectExcelVo> virtualProjectExcelVoList  = super.service.getListExcel(belongingCommunity);
        virtualProjectExcelVoList.forEach(a->{
            try {
                Long belongingCommunity1 =  a.getBelongingCommunity();
                CommunityClassEntity communityClassEntity = communityClassService.queryById(belongingCommunity1);
                a.setBelongingCommunityName(communityClassEntity.getName());
            }catch (Exception e){
                a.setBelongingCommunityName("");
            }

            try{
                Long belongingUnit = a.getBelongingUnit();
                UnitEntity unitEntity = unitService.queryById(belongingUnit);
                a.setBelongingUnitName(unitEntity.getName());

            }catch (Exception e){
                a.setBelongingUnitName("");
            }

            try{

                Long companyClass = a.getCompanyClass();
                DemandClassEntity demandClassEntity = demandClassService.queryById(companyClass);
                a.setCompanyClassName(demandClassEntity.getName());
            }catch (Exception e){
                a.setCompanyClassName("");
            }
            if(a.getStatus() == 1){
                a.setStatusName("未完成");
            }if(a.getStatus() == 2){
                a.setStatusName("已完成");
            }

        });

        ExcelBaseUtil.exportExcel(virtualProjectExcelVoList,"项目计划","项目计划",VirtualProjectExcelVo.class,"项目计划.xls",response);
    }



}