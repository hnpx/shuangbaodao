package cn.px.sys.modular.activity.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import cn.px.sys.modular.activity.vo.ActivityUserVo;
import cn.px.sys.modular.activity.vo.UserVo;
import cn.px.sys.modular.activity.wrapper.ActivityUserWrapper;
import cn.px.sys.modular.activity.wrapper.ActivityWrapper;
import cn.px.sys.modular.activity.wrapper.UserWrapper;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
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
 * (ActivityUserSign)表控制层
 *
 * @author
 * @since 2020-08-31 11:08:05
 */
@RestController
@RequestMapping("activityUserSign")
@Api(value = "(ActivityUserSign)管理")
public class ActivityUserSignController extends BaseController<ActivityUserSignEntity, ActivityUserSignService> {

    private static final String PREFIX = "/modular/activityUserSign";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private AllUserService allUserService;
    @Resource
    private ActivityUserWrapper activityUserWrapper;
    @Resource
    private UserWrapper userWrapper;

    @Resource
    private ActivityService activityService;


    @Autowired
    private MemberService memberService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private ReportCadreService reportCadreService;


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
    public Object query(ModelMap modelMap, ActivityUserSignEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ActivityUserSignEntity params) {
        Long userId = LoginContextHolder.getContext().getUserId();
        //TODO 数据验证
       ActivityEntity activityEntity= activityService.queryById(params.getActivityId());
       if(activityEntity.getBelongingCommunity()!= null & activityEntity.getBelongingUnit() != null){
           if(!activityEntity.getBelongingCommunity().equals(activityService.getCid(userId))){
               return super.setModelMap("400","您不属于指定社区，不能报名");
           }
           Long unit = unitService.getUnit(userId);
           if(!activityEntity.getBelongingUnit().equals(unit)){
               return super.setModelMap("400","您不属于指定单位，不能报名");
           }
       }
      Integer i =  activityEntity.getCurrentPerson();
      if(i==null){
          i=0;
      }
       if(i < activityEntity.getMaxPerson()){
           params.setUserId(LoginContextHolder.getContext().getUserId());
           //更新当前报名人数
           activityEntity.setCurrentPerson(i + 1);
           activityService.update(activityEntity);
           return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
       }else {
         return super.setModelMap("400","报名人数已满");

       }

    }

    @ApiOperation("后台手动报名")
    @PostMapping("/update/report")
    @ResponseBody
    public Object update1(ModelMap modelMap, ActivityUserSignEntity params) {
        //TODO 数据验证
        ActivityEntity activityEntity= activityService.queryById(params.getActivityId());
        Integer i =  activityEntity.getCurrentPerson();
        if(i==null){
            i=0;
        }
        if(i < activityEntity.getMaxPerson()){
            params.setUserId(params.getUserId());
            //更新当前报名人数
            activityEntity.setCurrentPerson(i + 1);
            activityService.update(activityEntity);
            return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        }else {
            return super.setModelMap("400","报名人数已满");

        }

    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ActivityUserSignEntity params) {
        Assert.notNull(params.getId(), "ID");
        ActivityUserSignEntity entity = new ActivityUserSignEntity();
        entity.setId(params.getId());
//        super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
        service.deleteByEntity(entity);
        return  setSuccessModelMap();
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ActivityUserSignEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ActivityUserSignEntity> p = super.service.query(params, page);
        List<ActivityUserSignEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "ActivityUserSign"), ActivityUserSignEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("根据用户id查询参与活动列表")
    @PutMapping("/read/listByUserId")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,@RequestParam("status") Integer status) {
      Long userId =  LoginContextHolder.getContext().getUserId();
      Page<Map<String,Object>> activityUser =  super.service.getListByUserId(page,pageSize,userId,status);
      Page<ActivityUserVo> pv = activityUserWrapper.getVoPage(activityUser);
      return super.setSuccessModelMap(pv);
    }


    @ApiOperation("根据活动id查询报名的人")
    @PutMapping("/read/listUserByUserId")
    @ResponseBody
    public Object query1(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,@RequestParam("activeId") Long activeId) {
        Page<Map<String,Object>> activityUser =  super.service.getListSignByUserId(page,pageSize,activeId);
        List<Map<String,Object>> list = activityUser.getRecords();
        list.forEach(a->{
            Long userId = (Long) a.get("userId");
            if (userId == null){
                return;
            }
            AllUserEntity allUserEntity =   allUserService.queryById(userId);
            if (allUserEntity == null){
                return;
            }
            Integer userType = allUserEntity.getType();
            if (userType == null){
                return;
            }
            //普通用户
            if (userType == 1){
                MemberEntity mEntity = new MemberEntity();
                mEntity.setUserId(userId);
                MemberEntity memberEntity = memberService.selectOne(mEntity);
                if (memberEntity==null){
                    return;
                }
                a.put("realName",memberEntity.getName());
                a.put("phone",memberEntity.getPhone());

            };

            //党员干部
            if (userType == 2) {
                ReportCadreEntity rEntity = new ReportCadreEntity();
                rEntity.setUserId(userId);
                ReportCadreEntity reportCadreEntity = reportCadreService.selectOne(rEntity);
                a.put("realName",reportCadreEntity.getName());
                a.put("phone",reportCadreEntity.getPhone());
            };

            //单位
            if (userType == 3) {
                UnitEntity uEntity = new UnitEntity();
                uEntity.setUserId(userId);
                UnitEntity unitEntity = unitService.selectOne(uEntity);
                a.put("realName",unitEntity.getName());
                a.put("phone",unitEntity.getPhone());
            }
        });


        Page<UserVo> pv = userWrapper.getVoPage(activityUser);
        return super.setSuccessModelMap(pv);
    }

   // @Scheduled(cron = "0 0 0 * * ?")
    @ApiOperation("未开启签到每天凌晨给人加积分")
    @PostMapping("/read/integeral")
    @ResponseBody
    public Object query1() {
       super.service.getIntegeral();
       return super.setSuccessModelMap();
    }

}