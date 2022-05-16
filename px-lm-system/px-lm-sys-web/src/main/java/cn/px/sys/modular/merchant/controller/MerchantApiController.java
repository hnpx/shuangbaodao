package cn.px.sys.modular.merchant.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.merchant.entity.MerchantEntity;
import cn.px.sys.modular.merchant.service.MerchantService;
import cn.px.sys.modular.merchant.vo.MerchantVo;
import cn.px.sys.modular.merchant.wrapper.MerchantWrapper;
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
 * 商家管理(Merchant)表控制层
 *
 * @author
 * @since 2020-09-02 14:37:40
 */
@RestController
@RequestMapping("api/merchant")
@Api(value = "商家管理(Merchant)管理")
public class MerchantApiController extends BaseController<MerchantEntity, MerchantService> {

    private static final String PREFIX = "/modular/merchant";
    @Autowired
    private HttpServletRequest request;

    @Resource
    private MerchantWrapper merchantWrapper;


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
    public Object query(ModelMap modelMap, MerchantEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, MerchantEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, MerchantEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, MerchantEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<MerchantEntity> p = super.service.query(params, page);
        List<MerchantEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("商家管理.xls", "Merchant"), MerchantEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize,
                        @RequestParam(required = false) Long classification,@RequestParam(required = false) String name,@RequestParam(required = false) Integer status,
                        @RequestParam(required = false) String contactPerson,
                        @RequestParam(required = false) String phone) {
    Page<Map<String,Object>> merchant =  super.service.getListPage(page,pageSize,classification,name,status,contactPerson,phone);
      Page<MerchantVo> pv =  merchantWrapper.getVoPage(merchant);
      return super.setSuccessModelMap(pv);
    }

    @ApiOperation("爱心益站详情")
    @GetMapping("/read/read/detail/{id}")
    @ResponseBody
    public Object selectOne(@PathVariable("id") Long id) {
        return super.setSuccessModelMap(super.service.getMerchantVo(id));
    }


    @ApiOperation("查询列表")
    @PutMapping("/read/listHome1")
    @ResponseBody
    public Object query1(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,
                         @RequestParam(required = false) Long classification,@RequestParam(required = false) String name) {
        Page<Map<String,Object>> merchant =  super.service.getListPage1(page,pageSize,classification,name);
        Page<MerchantVo> pv =  merchantWrapper.getVoPage(merchant);
        return super.setSuccessModelMap(pv);
    }

}