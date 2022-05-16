package cn.px.sys.modular.activity.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.constant.IsSignEnum;
import cn.px.sys.modular.activity.constant.SignEnum;
import cn.px.sys.modular.activity.constant.SignInOutEnum;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.job.OrderJobFactory;
import cn.px.sys.modular.activity.job.impl.OrderTaskParam;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import cn.px.sys.modular.activity.vo.ActivityVo;
import cn.px.sys.modular.activity.vo.NoteVo;
import cn.px.sys.modular.activity.wrapper.ActivityWrapper;
import cn.px.sys.modular.activity.wrapper.NoteWrapper;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.demand.constant.ApplyEnum;
import cn.px.sys.modular.system.entity.WxConfigEntity;
import cn.px.sys.modular.wx.controller.WxQr;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.entity.WxFile;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.sys.modular.wx.service.WxConfService;
import cn.px.sys.modular.wx.vo.WxConfVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.URLDecoder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.T;

/**
 * 活动管理(Activity)表控制层
 *
 * @author
 * @since 2020-08-27 15:09:54
 */
@RestController
@RequestMapping("activity")
@Api(value = "活动管理(Activity)管理")
public class ActivityController extends BaseController<ActivityEntity, ActivityService> {

    private static final String PREFIX = "/modular/activity";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ActivityWrapper activityWrapper;
    @Autowired
    private SftpHelper sftpHelper;
    @Resource
    private WxConfService wxConfService;
    @Resource
    private CommunityClassService communityClassService;
    @Resource
    private AllUserService allUserService;
    @Resource
    private NoteWrapper noteWrapper;
    @Autowired
    private OrderJobFactory orderJobFactory;
    @Autowired
    private ActivityUserSignService activityUserSignService;


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
       ActivityEntity activityEntity = super.service.queryById(id);
        activityEntity.setIntroduction(URLDecoder.decode(activityEntity.getIntroduction(),"UTF-8"));
        return super.setSuccessModelMap(modelMap, activityEntity);
    }

    @ApiOperation("查询")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ActivityEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ActivityEntity params) {
        //TODO 数据验证
        Long userId = LoginContextHolder.getContext().getUserId();
        Long cid = super.service.getCid(userId);
        params.setCid(cid);
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("社区认证")
    @PostMapping("/update2")
    @ResponseBody
    public Object update2(ModelMap modelMap, ActivityEntity params) {
        //TODO 数据验证
        ActivityEntity activityEntity = super.service.queryById(params.getId());
        if(activityEntity.getStartTime().compareTo(new Date())==1){
            return super.setModelMap("400","活动未开始无法认证");
        }else {
            activityUserSignService.getIntegeral(params.getId());
            params.setEndTime(new Date());
            return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        }

    }

    @ApiOperation("后台新增")
    @PostMapping("/update1")
    @ResponseBody
    @Transactional
    public Object update1(ModelMap modelMap, ActivityEntity params) throws NoSuchFieldException {
        //TODO 数据验证
        Long userId = LoginContextHolder.getContext().getUserId();
        try{
            Long cid = super.service.getCid(userId);
            params.setCid(cid);
        }catch (Exception e){
            params.setCid(1L);
        }
        params.setStatus(ApplyEnum.APPLY_STATUS_TRUE.getValue());
        ResponseEntity<ModelMap> object =(ResponseEntity<ModelMap>) super.update(modelMap, params, LoginContextHolder.getContext().getUserId());;
       /* if(params.getSignIn().equals(SignInOutEnum.SIGN_ENUM_TWO.getValue())){
            int minutes = super.service.getTime1(params.getEndTime());
            if(minutes> 0){
                ActivityEntity a = (ActivityEntity) object.getBody().get("activityEntity");
                this.orderJobFactory.createJob(new OrderTaskParam(a.getId().toString(),a.getId() , OrderTaskParam.TASK_ACTIVITY), minutes*60*1000);
            }

        }*/
        return super.setSuccessModelMap(object);
    }

    @ApiOperation("后台修改")
    @PostMapping("/edit")
    @ResponseBody
    public Object edit(ModelMap modelMap, ActivityEntity params) {
        //TODO 数据验证
        try {
            params.setIntroduction(URLEncoder.encode(params.getIntroduction(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ResponseEntity<ModelMap> object =(ResponseEntity<ModelMap>) super.update(modelMap, params, LoginContextHolder.getContext().getUserId());;
        /*if(params.getSignIn().equals(SignInOutEnum.SIGN_ENUM_TWO.getValue())){
            int minutes = super.service.getTime1(params.getEndTime());
            if(minutes> 0){
                ActivityEntity a = (ActivityEntity) object.getBody().get("activityEntity");
                this.orderJobFactory.createJob(new OrderTaskParam(a.getId().toString(),a.getId() , OrderTaskParam.TASK_ACTIVITY), minutes*60*1000);
            }
        }*/
        return super.setSuccessModelMap(object);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ActivityEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ActivityEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ActivityEntity> p = super.service.query(params, page);
        List<ActivityEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("活动管理.xls", "Activity"), ActivityEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/homeList")
    @ResponseBody
    public Object query(@RequestParam(required = false) String name,@RequestParam(required = false) Integer status,
                        @RequestParam(required = false) String contactPerson,@RequestParam(required = false) String phone,@RequestParam(required = false) Integer timeStatus) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> activityInfo = super.service.getActivity(name,status,userId,contactPerson,phone,timeStatus);
    Page<ActivityVo> pv =activityWrapper.getVoPage(activityInfo);
    return super.setSuccessModelMap(pv);
    }

    @ApiOperation("查询列表小程序端")
    @PutMapping("/read/homeListWx")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) Integer timeStatus) {
        Page<Map<String,Object>> activityInfo = super.service.getActivityWx(page,pageSize,timeStatus);
        Page<ActivityVo> pv =activityWrapper.getVoPage(activityInfo);
        return super.setSuccessModelMap(pv);
    }

    @ApiOperation("获取签到二维码")
    @PutMapping("/read/getminiqrQrTwo")
    public Object getminiqrQrTwo(Long aid, org.springframework.ui.Model modelMap) {
       ActivityEntity activityEntity = super.service.queryById(aid);
        if(activityEntity.getSignIn().equals(SignInOutEnum.SIGN_ENUM_ONE.getValue())){
           // String path="/pic.jpg";
            WxConfVo wxConfig= wxConfService.getWxConfEntity();
            String appid = wxConfig.getAppid();
            String secret = wxConfig.getSecret();
            String type = "type";
            WxQr wxQr = new WxQr();
            Object object = wxQr.getminiqrQrTwo(aid, wxQr.getAccessToken(appid, secret).get("access_token").toString(), request, type, sftpHelper);
    /*    JSONObject jsonObject = JSONObject.fromObject(object);
        String urlList = jsonObject.getString("data");*/
            // super.service.downloadPicture(object.toString(),path);
            //super.service.getD(object.toString());
            return setSuccessModelMap(object);
        }else {
            return super.setModelMap("400","此活动不需要签到签出，无法生成二维码");
        }

    }

    @ApiOperation("活动详情")
    @GetMapping("/read/read/detail/{id}")
    @ResponseBody
    public Object selectOne1(@PathVariable("id") Long id) {
        Long userId = LoginContextHolder.getContext().getUserId();
        return super.setSuccessModelMap(super.service.getActivityEntity(id,userId));
    }


    @ApiOperation("签到")
    @PostMapping("/update/signIn")
    @ResponseBody
    public Object update1(@RequestParam("aid") Long aid) {
        //TODO 数据验证
        int i = super.service.getSignIn(aid,LoginContextHolder.getContext().getUserId());
        if(i==0){
            return super.setModelMap("400","此用户未报名无法进行签到");
        }
        return super.setSuccessModelMap();
    }


    @ApiOperation("签出")
    @PostMapping("/update/signOut")
    @ResponseBody
    public Object update2(@RequestParam("aid") Long aid) {
        //TODO 数据验证
      int i =  super.service.getSignOut(aid,LoginContextHolder.getContext().getUserId());
      if(i==0){
          return super.setModelMap("400"," 此用户未报名，无法进行签出");
      }else if(i==3){
          return super.setModelMap("400"," 此活动未达到签出时间，不能签出");
      }
        return super.setSuccessModelMap();
    }



    @ApiOperation("消息中心")
    @PutMapping("/read/note")
    @ResponseBody
    public Object query1(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> note = super.service.getNote(page,pageSize,userId);
        Page<NoteVo> pv =noteWrapper.getVoPage(note);
        return super.setSuccessModelMap(pv);
    }


    @ApiOperation("审核列表")
    @PutMapping("/read/note1")
    @ResponseBody
    public Object query2(@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> note = super.service.getApply(page,pageSize,userId);
        Page<NoteVo> pv =noteWrapper.getVoPage(note);
        return super.setSuccessModelMap(pv);
    }

    @ApiOperation("测试")
    @PutMapping("/read/test")
    public void get(){
        Long id = 1000000048591404L;
        this.orderJobFactory.createJob(new OrderTaskParam(id.toString(),id , OrderTaskParam.TASK_PROJECT), 60*1000);
    }

}