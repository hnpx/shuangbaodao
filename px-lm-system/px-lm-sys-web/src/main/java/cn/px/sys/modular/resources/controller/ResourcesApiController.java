package cn.px.sys.modular.resources.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.resources.entity.ResourcesEntity;
import cn.px.sys.modular.resources.service.ResourcesService;
import cn.px.sys.modular.resources.vo.ResourcesVo;
import cn.px.sys.modular.resources.wrapper.ResourcesWrapper;
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
 * 资源管理(Resources)表控制层
 *
 * @author
 * @since 2020-09-04 12:32:31
 */
@RestController
@RequestMapping("api/resources")
@Api(value = "资源管理(Resources)管理")
public class ResourcesApiController extends BaseController<ResourcesEntity, ResourcesService> {

    private static final String PREFIX = "/modular/resources";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ResourcesWrapper resourcesWrapper;


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
    public Object query(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize,@RequestParam("resourcesClass") Long resourcesClass) {
     Page<Map<String,Object>> resources = super.service.getPageList(page,pageSize,resourcesClass);
        Page<ResourcesVo> pv =  resourcesWrapper.getVoPage(resources);
        return super.setSuccessModelMap(pv);
    }


}