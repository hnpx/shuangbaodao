package cn.px.sys.modular.resources.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.resources.entity.ResourcesUserEntity;
import cn.px.sys.modular.resources.service.ResourcesUserService;
import cn.px.sys.modular.resources.vo.ResourcesUserVo;
import cn.px.sys.modular.resources.wrapper.ResourcesUserWrapper;
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
 * (ResourcesUser)表控制层
 *
 * @author
 * @since 2020-09-04 15:40:49
 */
@RestController
@RequestMapping("resourcesUser")
@Api(value = "(ResourcesUser)管理")
public class ResourcesUserController extends BaseController<ResourcesUserEntity, ResourcesUserService> {

    private static final String PREFIX = "/modular/resourcesUser";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ResourcesUserWrapper resourcesUserWrapper;


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
    public Object query(ModelMap modelMap, ResourcesUserEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ResourcesUserEntity params) {
        //TODO 数据验证
        params.setUserId(LoginContextHolder.getContext().getUserId());
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ResourcesUserEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ResourcesUserEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ResourcesUserEntity> p = super.service.query(params, page);
        List<ResourcesUserEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "ResourcesUser"), ResourcesUserEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) String name,@RequestParam(required = false) Long rid) {
     Page<Map<String,Object>> resourcesUser =  super.service.getHomeList(page,pageSize,name,rid);
     Page<ResourcesUserVo> pv = resourcesUserWrapper.getVoPage(resourcesUser);
      return super.setSuccessModelMap(pv);
    }


}