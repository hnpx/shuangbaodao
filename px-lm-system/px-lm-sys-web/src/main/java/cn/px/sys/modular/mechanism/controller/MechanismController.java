package cn.px.sys.modular.mechanism.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.mechanism.entity.MechanismEntity;
import cn.px.sys.modular.mechanism.service.MechanismService;
import cn.px.sys.modular.mechanism.vo.MechanismVo;
import cn.px.sys.modular.mechanism.wrapper.MechanismWrapper;
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
 * 机构管理(Mechanism)表控制层
 *
 * @author
 * @since 2020-09-01 11:25:49
 */
@RestController
@RequestMapping("api/mechanism")
@Api(value = "机构管理(Mechanism)管理")
public class MechanismController extends BaseController<MechanismEntity, MechanismService> {

    private static final String PREFIX = "/modular/mechanism";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private MechanismWrapper mechanismWrapper;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("详情")
    @GetMapping("/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
        return super.setSuccessModelMap(modelMap, super.service.queryById(id));
    }

    @ApiOperation("查询")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, MechanismEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, MechanismEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, MechanismEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, MechanismEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<MechanismEntity> p = super.service.query(params, page);
        List<MechanismEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("机构管理.xls", "Mechanism"), MechanismEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listWx")
    @ResponseBody
    public Object query1(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize) {
        Page<Map<String,Object>> machanism = super.service.getList(page,pageSize);
        Page<MechanismVo> pv = mechanismWrapper.getVoPage(machanism);
        return super.setSuccessModelMap(pv);
    }


}