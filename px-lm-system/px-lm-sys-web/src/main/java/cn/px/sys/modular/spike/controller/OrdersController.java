package cn.px.sys.modular.spike.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.core.constant.state.Order;
import cn.px.sys.modular.spike.constant.ExchangeStatusEnum;
import cn.px.sys.modular.spike.entity.OrdersEntity;
import cn.px.sys.modular.spike.service.OrdersService;
import cn.px.sys.modular.spike.vo.OrderVo;
import cn.px.sys.modular.spike.wrapper.OrderWrapper;
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
 * 兑换订单(Orders)表控制层
 *
 * @author
 * @since 2020-09-09 09:25:37
 */
@RestController
@RequestMapping("orders")
@Api(value = "兑换订单(Orders)管理")
public class OrdersController extends BaseController<OrdersEntity, OrdersService> {

    private static final String PREFIX = "/modular/orders";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private OrderWrapper orderWrapper;


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
    public Object query(ModelMap modelMap, OrdersEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, OrdersEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, OrdersEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, OrdersEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<OrdersEntity> p = super.service.query(params, page);
        List<OrdersEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("兑换订单.xls", "Orders"), OrdersEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) String nickname,@RequestParam(required = false) String productName,@RequestParam(required = false) Integer status) {

      Page<Map<String,Object>> order = super.service.getHomeList(nickname,productName,status);
      Page<OrderVo> pv =  orderWrapper.getVoPage(order);
      return super.setSuccessModelMap(pv);
    }



    @ApiOperation("是否兑换")
    @PostMapping("/update/isExchange")
    @ResponseBody
    public Object updateIsOffShelf(ModelMap modelMap,Long id) {

        OrdersEntity param = super.service.queryById(id);
        if(param.getStatus().equals(ExchangeStatusEnum.EXCHANGE_STATUS_ENUM_ONE.getValue())){
            param.setStatus(ExchangeStatusEnum.EXCHANGE_STATUS_ENUM_TWO.getValue());
            return super.update(modelMap,param,LoginContextHolder.getContext().getUserId());
        }else if(param.getStatus().equals(ExchangeStatusEnum.EXCHANGE_STATUS_ENUM_TWO.getValue())){
            return super.setModelMap("400","商品已兑换无法修改,修改不生效！");
        }
        return null;
    }

}