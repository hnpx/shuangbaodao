package cn.px.sys.modular.wx.controller;

import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.mapper.ReportCadreMapper;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.wx.vo.AllUserVo;
import cn.px.sys.modular.wx.vo.CadreVo;
import cn.px.sys.modular.wx.vo.UserReVo;
import cn.px.sys.modular.wx.wrapper.AllUserWrapper;
import cn.px.sys.modular.wx.wrapper.UserReWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.internal.$Gson$Preconditions;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import cn.px.base.auth.context.LoginContextHolder;
import io.swagger.annotations.Api;
import cn.px.base.core.BaseController;
import cn.px.base.support.Assert;
import cn.hutool.core.bean.BeanUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;


import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * (AllUser)表控制层
 *
 * @author
 * @since 2020-08-29 11:03:35
 */
/*@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务*/
@RestController
@RequestMapping("allUser")
@Api(value = "(AllUser)管理")
public class AllUserController extends BaseController<AllUserEntity, AllUserService> {

    private static final String PREFIX = "/modular/allUser";
    @Autowired
    private HttpServletRequest request;

    @Resource
    private AllUserWrapper allUserWrapper;

    @Resource
    private UserReWrapper userReWrapper;

    @Resource
    private ActivityService activityService;
    @Resource
    private ReportCadreService reportCadreService;

    @Resource
    private ReportCadreMapper reportCadreMapper;
    @Resource
    private IntegralRecordService integralRecordService;

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
    public Object query(ModelMap modelMap, AllUserEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, AllUserEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, AllUserEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, AllUserEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<AllUserEntity> p = super.service.query(params, page);
        List<AllUserEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "AllUser"), AllUserEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("查询用户信息")
    @PutMapping("/read/AllUserList")
    @ResponseBody
    public Object query(@RequestParam("openid") String openid) {
      AllUserEntity  allUser = super.service.readByOpenid(openid);
      AllUserVo allUserVo = super.service.getAllUserVo(allUser);
      return super.setSuccessModelMap(allUserVo);
    }


    @ApiOperation("双报到")
    @PostMapping("/read/report")
    @ResponseBody
    public Object query() {
       Long userId = LoginContextHolder.getContext().getUserId();
      int  i =  super.service.report(userId);
      if(i==1){
          return super.setSuccessModelMap();
      }else if(i==0){
          return super.setModelMap("400","您的身份不符合双报到");
      } else if(i==2){
          return super.setModelMap("400","您今年已经进行双报到");
      }else {
          return super.setSuccessModelMap();
      }

    }


    @Scheduled(cron = "0 0 0 1 1 ?")
    @ApiOperation("双报到初始化信息")
    @PostMapping("/read/reportInit")
    @ResponseBody
    public Object query1() {
     super.service.initReport();
     return super.setSuccessModelMap();
    }


    @ApiOperation("双报到用户信息")
    @PostMapping("/read/getCardreIntegral")
    @ResponseBody
    public Object getCardreIntegral(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
      Page<Map<String,Object>> user =  super.service.getAllUserEntityByType(page,pageSize);
      Page<CadreVo> pv = allUserWrapper.getVoPage(user);
      return super.setSuccessModelMap(pv);
    }

    @ApiOperation("双报到用户信息所属单位下的信息")
    @PostMapping("/read/getCardreIntegralByUnit")
    @ResponseBody
    public Object getCardreIntegral1(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        Long userId = LoginContextHolder.getContext().getUserId();
        //Long userId = 1000000564125490L;
        ReportCadreEntity reportCadreEntity =reportCadreMapper.getReportCadreEntityByUserId(userId);
        Page<Map<String,Object>> user =  super.service.getAllUserEntityByType1(page,pageSize,reportCadreEntity.getBelongingUnit());
        Page<CadreVo> pv = allUserWrapper.getVoPage(user);
        return super.setSuccessModelMap(pv);
    }

    @ApiOperation("根据用户id查询内容")
    @PostMapping("/update/id")
    @ResponseBody
    public Object update(@RequestParam("userId") Long userId) {
     return super.setSuccessModelMap(super.service.getName(userId));

    }


    @ApiOperation("查询用户信息报名")
    @PutMapping("/read/AllUserList/report")
    @ResponseBody
    public Object query(@RequestParam(required = false) Long activeId, @RequestParam(required = false) String nickname) {

          Page<Map<String,Object>> user =  super.service.getListUser(activeId,nickname);
          Page<UserReVo> pv = userReWrapper.getVoPage(user,activeId);
          return super.setSuccessModelMap(pv);
    }


    @ApiOperation("查询用户信息报名")
    @PutMapping("/read/AllUserList/report1")
    @ResponseBody
    public Object query1(@RequestParam(required = false) Long pid,@RequestParam(required = false) String nickname) {

        Page<Map<String,Object>> user =  super.service.getListUser1(pid,nickname);
        Page<UserReVo> pv = userReWrapper.getVoPage(user,pid);
        return super.setSuccessModelMap(pv);
    }

    @ApiOperation("查询用户信息报名")
    @PutMapping("/read/AllUserList/report2")
    @ResponseBody
    public Object query2(@RequestParam(required = false) Long pid,@RequestParam(required = false) String name,
                         @RequestParam(required = false) String phone,@RequestParam(required = false) String unit) {

        Page<Map<String,Object>> user =  super.service.getListUser2(pid,name,phone,unit);
       // Page<UserReVo> pv = userReWrapper.getVoPage(user,pid);
        user.getRecords().forEach(a->{
            a.put("activityId",pid);
        });
        return super.setSuccessModelMap(user);
    }



    @ApiOperation("党员干部公益积分计算")
    @PutMapping("/read/AllUserList/jf")
    @ResponseBody
    public Object get(){
        ReportCadreEntity reportCadreEntity = new ReportCadreEntity();
        reportCadreEntity.setIsBind(1);
      List<ReportCadreEntity> reportCadreEntityList =  reportCadreService.queryList(reportCadreEntity);
        for (ReportCadreEntity reportCadreEntity1:reportCadreEntityList) {
         int i = 0;
         List<IntegralRecordEntity> integralRecordEntityList =   integralRecordService.getEntityByUser(reportCadreEntity1.getUserId());
            for (IntegralRecordEntity integralRecordEntity:integralRecordEntityList) {
                if(integralRecordEntity.getType() == 1){
                    i = i + integralRecordEntity.getIntegral();
                }else if(integralRecordEntity.getType() == 2){
                    i = i - integralRecordEntity.getIntegral();
                }
            }
            AllUserEntity allUserEntity = new AllUserEntity();
            allUserEntity.setId(reportCadreEntity1.getUserId());
            allUserEntity.setIntegral(i);
            allUserEntity.setRemainingPoints(i);
            super.service.update(allUserEntity);
        }
        return super.setSuccessModelMap();

    }


}