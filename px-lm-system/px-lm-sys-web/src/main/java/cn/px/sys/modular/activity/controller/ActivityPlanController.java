package cn.px.sys.modular.activity.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.entity.ActivityPlanEntity;
import cn.px.sys.modular.activity.service.ActivityPlanService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (ActivityPlan)表控制层
 *
 * @author
 * @since 2020-09-28 09:16:29
 */
@RestController
@RequestMapping("activityPlan")
@Api(value = "(ActivityPlan)管理")
public class ActivityPlanController extends BaseController<ActivityPlanEntity, ActivityPlanService> {

    private static final String PREFIX = "/modular/activityPlan";
    @Autowired
    private HttpServletRequest request;


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

//    @ApiOperation("查询")
//    @PutMapping("/read/list")
//    @ResponseBody
//    public Object query(ModelMap modelMap, ActivityPlanEntity params) {
//        Page<Long> page = LayuiPageFactory.defaultPage();
//        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
//        return super.query(modelMap, params, page);
//    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ActivityPlanEntity params) {
        //TODO 数据验证

        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ActivityPlanEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ActivityPlanEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ActivityPlanEntity> p = super.service.query(params, page);
        List<ActivityPlanEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "ActivityPlan"), ActivityPlanEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("查询")
    @PutMapping("/read/list")
    public Object query(Long id) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        return setSuccessModelMap(service.getlist(page,id));
    }

}