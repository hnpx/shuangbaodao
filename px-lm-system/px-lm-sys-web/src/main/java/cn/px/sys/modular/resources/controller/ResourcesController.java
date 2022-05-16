package cn.px.sys.modular.resources.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.project.vo.ProjectVo;
import cn.px.sys.modular.resources.entity.ResourcesEntity;
import cn.px.sys.modular.resources.service.ResourcesService;
import cn.px.sys.modular.resources.vo.ResourcesManVo;
import cn.px.sys.modular.resources.vo.ResourcesVo;
import cn.px.sys.modular.resources.wrapper.ResourcesManWrapper;
import cn.px.sys.modular.resources.wrapper.ResourcesWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.URLDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源管理(Resources)表控制层
 *
 * @author
 * @since 2020-09-04 12:32:31
 */
@RestController
@RequestMapping("resources")
@Api(value = "资源管理(Resources)管理")
public class ResourcesController extends BaseController<ResourcesEntity, ResourcesService> {

    private static final String PREFIX = "/modular/resources";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ResourcesWrapper resourcesWrapper;

    @Resource
    private ResourcesManWrapper resourcesManWrapper;
    @Resource
    private ActivityService activityService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
       ResourcesEntity resourcesEntity = super.service.queryById(id);
        resourcesEntity.setContent(URLDecoder.decode(resourcesEntity.getContent(),"UTF-8"));
        return super.setSuccessModelMap(modelMap, resourcesEntity);
    }

    @ApiOperation("查询")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ResourcesEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ResourcesEntity params) {
        //TODO 数据验证
        Long userId =   LoginContextHolder.getContext().getUserId();
        Long cid = activityService.getCid(userId);
        params.setCid(cid);
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("审核")
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(ModelMap modelMap, ResourcesEntity params) {
        //TODO 数据验证
        try {
            params.setContent(URLEncoder.encode(params.getContent(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ResourcesEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ResourcesEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ResourcesEntity> p = super.service.query(params, page);
        List<ResourcesEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("资源管理.xls", "Resources"), ResourcesEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询资源列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) String name,@RequestParam(required = false) Integer status,@RequestParam(required = false) String contactPerson,@RequestParam(required = false) String phone) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> resources = super.service.getPageListMan(name,status,userId,contactPerson,phone);
        Page<ResourcesManVo> pv =  resourcesManWrapper.getVoPage(resources);
        return super.setSuccessModelMap(pv);
    }

    @ApiOperation("我的资源")
    @PutMapping("/read/listHomeMySelf")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> resources = super.service.getPageListMySelf(page,pageSize,userId);
        Page<ResourcesManVo> pv =  resourcesManWrapper.getVoPage(resources);
        return super.setSuccessModelMap(pv);
    }


}