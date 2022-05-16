package cn.px.sys.modular.spike.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.merchant.service.MerchantService;
import cn.px.sys.modular.spike.entity.ProductEntity;
import cn.px.sys.modular.spike.service.ProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品管理(Product)表控制层
 *
 * @author makejava
 * @since 2020-09-07 16:27:33
 */
@RestController
@Api(value = "产品管理(Product)管理", tags = "产品管理(Product)管理")
public class ProductController extends BaseController<ProductEntity, ProductService> {

    private static final String PREFIX = "/modular/product";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    MerchantService merchantService;
    @Resource
    private IntegralRecordService integralRecordService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("product/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
        ProductEntity productEntity=service.queryById(id);
        productEntity.setMerchantEntity(merchantService.queryById(productEntity.getMerchantId()));
        return super.setSuccessModelMap(modelMap, productEntity);
    }

    @ApiOperation("查询产品列表")
    @PutMapping("product/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ProductEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
       return super.query(modelMap, params, page);
    }

    @ApiOperation("查询积分兑换列表")
    @PutMapping("api/product/read/pointsExchange")
    public Object pointsExchange(Long page,Long limit) {
        return setSuccessModelMap(service.pointsExchange(page,limit));
    }

    @ApiOperation("查询积分兑换详情")
    @GetMapping("/getPointsExchangeDetails")
    Object getPointsExchangeDetails(Long productId){
        return setSuccessModelMap(service.getPointsExchangeDetails(productId));

    }


    @ApiOperation("更新")
    @PostMapping("product/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ProductEntity params) {
        //TODO 数据验证

        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @PostMapping("product/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ProductEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("product/excel")
    public Object exportExcel(ModelMap modelMap, ProductEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ProductEntity> p = super.service.query(params, page);
        List<ProductEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("产品管理.xls", "Product"), ProductEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("兑换商品和秒杀")
    @PutMapping("product/read/exchange/order")
    public Object redeemNow(@RequestParam("pid") Long pid) {
       String msg =  integralRecordService.getIntegralRecordEntityBySpike(pid, LoginContextHolder.getContext().getUserId());
       if(msg == null || msg.length() <= 0){
           return super.setSuccessModelMap();

       }else {
           return super.setModelMap("400",msg);
       }

    }

}