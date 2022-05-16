package cn.px.sys.modular.project.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.support.file.SftpHelper;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.constant.SignInOutEnum;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.job.OrderJobFactory;
import cn.px.sys.modular.activity.job.impl.OrderTaskParam;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.demand.constant.ApplyEnum;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.service.ProjectService;
import cn.px.sys.modular.project.vo.ProjectVo;
import cn.px.sys.modular.project.wrapper.ProjectWrapper;
import cn.px.sys.modular.wx.controller.WxQr;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.sys.modular.wx.service.WxConfService;
import cn.px.sys.modular.wx.vo.WxConfVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.URLDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目管理(Project)表控制层
 *
 * @author
 * @since 2020-09-02 20:20:08
 */
@RestController
@RequestMapping("project")
@Api(value = "项目管理(Project)管理")
public class ProjectController extends BaseController<ProjectEntity, ProjectService> {

    private static final String PREFIX = "/modular/project";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ProjectWrapper projectWrapper;
    @Resource
    private WxConfService wxConfService;
    @Resource
    private SftpHelper sftpHelper;
    @Resource
    private CommunityClassService communityClassService;
    @Resource
    private ActivityService activityService;
    @Autowired
    private OrderJobFactory orderJobFactory;

   @Autowired
   private  ActivityUserSignService  activityUserSignService;
   @Autowired
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
      ProjectEntity projectEntity =  super.service.queryById(id);
      projectEntity.setContent(URLDecoder.decode(projectEntity.getContent(),"UTF-8"));
        return super.setSuccessModelMap(modelMap, projectEntity);
    }

    @ApiOperation("查询")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ProjectEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ProjectEntity params) {
        //TODO 数据验证
        Long userId = LoginContextHolder.getContext().getUserId();
        if(userId==1){

        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
            params.setBelongingCommunity(communityClassEntity.getId());
        }
        params.setStatus(ApplyEnum.APPLY_STATUS_TRUE.getValue());
        ResponseEntity<ModelMap> object =(ResponseEntity<ModelMap>) super.update(modelMap, params, LoginContextHolder.getContext().getUserId());;
      /*  if(params.getSignIn().equals(SignInOutEnum.SIGN_ENUM_TWO.getValue())){
            int minutes = activityService.getTime1(params.getEndTime());
            if(minutes> 0){
                ProjectEntity a = (ProjectEntity) object.getBody().get("projectEntity");
                this.orderJobFactory.createJob(new OrderTaskParam(a.getId().toString(),a.getId() , OrderTaskParam.TASK_PROJECT), minutes*60*1000);
            }
        }*/
        return super.setSuccessModelMap(object);
    }

    @ApiOperation("审核")
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(ModelMap modelMap, ProjectEntity params) {
        //TODO 数据验证
        params.setStatus(ApplyEnum.APPLY_STATUS_TRUE.getValue());
        ResponseEntity<ModelMap> object =(ResponseEntity<ModelMap>) super.update(modelMap, params, LoginContextHolder.getContext().getUserId());;
      /*  if(params.getSignIn().equals(SignInOutEnum.SIGN_ENUM_TWO.getValue())){
            int minutes = activityService.getTime1(params.getEndTime());
            if(minutes> 0){
                ProjectEntity a = (ProjectEntity) object.getBody().get("projectEntity");
                this.orderJobFactory.createJob(new OrderTaskParam(a.getId().toString(),a.getId() , OrderTaskParam.TASK_PROJECT), minutes*60*1000);
            }
        }*/

        return super.setSuccessModelMap(object);
    }

    @ApiOperation("审核")
    @PostMapping("/update4")
    @ResponseBody
    public Object update4(ModelMap modelMap, ProjectEntity params) {
        //TODO 数据验证
        try {
            params.setContent(URLEncoder.encode(params.getContent(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ResponseEntity<ModelMap> object =(ResponseEntity<ModelMap>) super.update(modelMap, params, LoginContextHolder.getContext().getUserId());;
      /*  if(params.getSignIn().equals(SignInOutEnum.SIGN_ENUM_TWO.getValue())){
            int minutes = activityService.getTime1(params.getEndTime());
            if(minutes> 0){
                ProjectEntity a = (ProjectEntity) object.getBody().get("projectEntity");
                this.orderJobFactory.createJob(new OrderTaskParam(a.getId().toString(),a.getId() , OrderTaskParam.TASK_PROJECT), minutes*60*1000);
            }
        }*/

        return super.setSuccessModelMap(object);
    }

    @ApiOperation("小程序端添加项目")
    @PostMapping("/wx/update")
    @ResponseBody
    public Object update3(ModelMap modelMap, ProjectEntity params) {
        Long userId = LoginContextHolder.getContext().getUserId();
        if(allUserService.queryById(userId).getType()==2){ //党员干部
            params.setStatus(ApplyEnum.APPLY_STATUS_WAIT.getValue());
            return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        }else {
            return super.setModelMap("400","您不属于党员干部,不能发布项目！");
        }

    }

    @ApiOperation("新增认证")
    @PostMapping("/update2")
    @ResponseBody
    public Object update2(ModelMap modelMap, ProjectEntity params) {
       ProjectEntity projectEntity = super.service.queryById(params.getId());
       if(projectEntity.getStartTime().compareTo(new Date())==1){
           return super.setModelMap("400","项目未开始无法认证");
       }else {
           activityUserSignService.getIntegeral1(params.getId());
           params.setEndTime(new Date());
           return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
       }
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ProjectEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ProjectEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ProjectEntity> p = super.service.query(params, page);
        List<ProjectEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("项目管理.xls", "Project"), ProjectEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) String name,@RequestParam(required = false) String contactPerson,
                        @RequestParam(required = false) String phone,@RequestParam(required = false) Integer timeStatus,@RequestParam(required = false) Integer status,
                        @RequestParam(required = false) Long companyClass) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Long cid;
        if(userId == 1){
            cid = null;
        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
             cid = communityClassEntity.getId();
        }

      Page<Map<String,Object>> project = super.service.getListPage(name,cid,contactPerson,phone,timeStatus,status,companyClass);
      Page<ProjectVo> pv =  projectWrapper.getVoPage(project);
      return super.setSuccessModelMap(pv);
    }

    @ApiOperation("项目详情")
    @GetMapping("/read/read/detail/{id}")
    @ResponseBody
    public Object selectOne1(@PathVariable("id") Long id) {
        Long userId = LoginContextHolder.getContext().getUserId();
        return super.setSuccessModelMap(super.service.getProjectVo(id,userId));
    }


    @ApiOperation("获取签到二维码")
    @PutMapping("/read/getminiqrQrTwo")
    public Object getminiqrQrTwo(Long pid, org.springframework.ui.Model modelMap) {
        ProjectEntity projectEntity = super.service.queryById(pid);
        if(projectEntity.getSignIn().equals(SignInOutEnum.SIGN_ENUM_ONE.getValue())){
            // String path="/pic.jpg";
            WxConfVo wxConfig= wxConfService.getWxConfEntity();
            String appid = wxConfig.getAppid();
            String secret = wxConfig.getSecret();
            String type = "type";
            WxQr wxQr = new WxQr();
            Object object = wxQr.getminiqrQrTwo2(pid, wxQr.getAccessToken(appid, secret).get("access_token").toString(), request, type, sftpHelper);
            return setSuccessModelMap(object);
        }else {
            return super.setModelMap("400","此项目不需要签到签出，无法生成二维码");
        }

    }


    @ApiOperation("签到")
    @PostMapping("/update/signIn")
    @ResponseBody
    public Object update1(@RequestParam("pid") Long pid) {
        //TODO 数据验证
        int i = super.service.getSignIn(pid,LoginContextHolder.getContext().getUserId());
        if(i==0){
            return super.setModelMap("400","此用户未认领无法进行签到");
        }
        return super.setSuccessModelMap();
    }


    @ApiOperation("签出")
    @PostMapping("/update/signOut")
    @ResponseBody
    public Object update2(@RequestParam("pid") Long pid) {
        //TODO 数据验证
        int i =  super.service.getSignOut(pid,LoginContextHolder.getContext().getUserId());
        if(i==0){
            return super.setModelMap("400"," 此用户未认领，无法进行签出");
        } else if(i==3){
            return super.setModelMap("400"," 此项目未到达签出的时间，无法进行签出");
        }
        return super.setSuccessModelMap();
    }





    public Object rep(ModelMap modelMap, ProjectEntity params){

        Long userId = LoginContextHolder.getContext().getUserId();
        if(userId==1){
        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
            params.setBelongingCommunity(communityClassEntity.getId());
        }

        ResponseEntity<ModelMap> object =(ResponseEntity<ModelMap>) super.update(modelMap, params, LoginContextHolder.getContext().getUserId());;
        if(params.getSignIn().equals(SignInOutEnum.SIGN_ENUM_TWO.getValue())){
            int minutes = activityService.getTime1(params.getEndTime());
            if(minutes> 0){
                ProjectEntity a = (ProjectEntity) object.getBody().get("projectEntity");
                this.orderJobFactory.createJob(new OrderTaskParam(a.getId().toString(),a.getId() , OrderTaskParam.TASK_PROJECT), minutes*60*1000);
            }
        }

        return super.setSuccessModelMap(object);
    }

}