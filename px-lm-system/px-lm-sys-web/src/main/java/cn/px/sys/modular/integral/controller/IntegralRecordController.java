package cn.px.sys.modular.integral.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integral.vo.IntegralRecordVo;
import cn.px.sys.modular.integral.wrapper.IntegralRecordWrapper;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分管理(IntegralRecord)表控制层
 *
 * @author
 * @since 2020-09-07 09:35:58
 */
@RestController
@RequestMapping("integralRecord")
@Api(value = "积分管理(IntegralRecord)管理")
public class IntegralRecordController extends BaseController<IntegralRecordEntity, IntegralRecordService> {

    private static final String PREFIX = "/modular/integralRecord";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private IntegralRecordWrapper integralRecordWrapper;
    @Resource
    private AllUserService allUserService;



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
    public Object query(ModelMap modelMap, IntegralRecordEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, IntegralRecordEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }


    @ApiOperation("手动加积分")
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(ModelMap modelMap, IntegralRecordEntity params) {
        //TODO 数据验证
        params.setType(1);
        AllUserEntity allUserEntity = allUserService.queryById(params.getUserId());
        Integer integral = null;
        if(allUserEntity.getIntegral() == null){
            integral=0;
        }else{
            integral = allUserEntity.getIntegral();
        }
        Integer remainingPoints = null;
        if(allUserEntity.getRemainingPoints()== null){
            remainingPoints = 0;
        }else {
            remainingPoints = allUserEntity.getRemainingPoints();
        }
        allUserEntity.setIntegral(integral + params.getIntegral());
        allUserEntity.setRemainingPoints(remainingPoints+params.getIntegral());
        allUserService.update(allUserEntity);
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }


    @ApiOperation("手动减积分")
    @PostMapping("/update2")
    @ResponseBody
    public Object update2(ModelMap modelMap, IntegralRecordEntity params) {
        //TODO 数据验证
        params.setType(2);
        AllUserEntity allUserEntity = allUserService.queryById(params.getUserId());
        Integer remainingPoints = null;
        if(allUserEntity.getRemainingPoints()== null){
            remainingPoints = 0;
        }else {
            remainingPoints = allUserEntity.getRemainingPoints();
        }
        allUserEntity.setRemainingPoints(remainingPoints - params.getIntegral());
        allUserService.update(allUserEntity);
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, IntegralRecordEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, IntegralRecordEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<IntegralRecordEntity> p = super.service.query(params, page);
        List<IntegralRecordEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("积分管理.xls", "IntegralRecord"), IntegralRecordEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("积分记录")
    @PostMapping("/update/integral/record")
    @ResponseBody
    public Object update1(@RequestParam("aid") Long aid) {
        //TODO 数据验证

        IntegralRecordEntity integralRecordEntity = super.service.getIntegralRecordEntity(aid,LoginContextHolder.getContext().getUserId());
        return super.setSuccessModelMap(integralRecordEntity);

    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) Long userId) {
        if(userId == null){
           userId =  LoginContextHolder.getContext().getUserId();
        }
      Page<Map<String,Object>> integralRecord = super.service.getHomeList(userId);
      Page<IntegralRecordVo> pv =  integralRecordWrapper.getVoPage(integralRecord);
      return super.setSuccessModelMap(pv);
    }


    @ApiOperation("商家兑换积分记录")
    @PostMapping("/update/merchat/integral/record")
    @ResponseBody
    public Object update1(@RequestParam("mid") Long mid,@RequestParam("integral") Integer integral) {
     Long userId =   LoginContextHolder.getContext().getUserId();
      String msg =  super.service.allUser(mid,integral,userId);
        if(msg == null || msg.length() <= 0){
          return super.setSuccessModelMap();
        }else {
          return super.setModelMap("400",msg);
       }
    }


    @Scheduled(cron = "0 0 0 * * ?")
    @ApiOperation("社区积分统计")
    @ResponseBody
    public Object query1() {
     super.service.updateIntegral();
     return super.setSuccessModelMap();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @ApiOperation("单位积分统计")
    @ResponseBody
    public Object query2() {
        super.service.updateIntegral1();
        return super.setSuccessModelMap();
    }



    @ApiOperation("查询兑换记录")
    @PutMapping("/read/list/exchange")
    @ResponseBody
    public Object exchange(@RequestParam("mid") Long mid) {
       Page<Map<String,Object>> integral = super.service.getRecord(mid);
        integral.getRecords().forEach(a->{
            Long userId = Long.parseLong(a.get("userId").toString());
            a.put("userName",allUserService.queryById(userId).getNickname());
        });
        return super.setSuccessModelMap(integral);
    }

}