package cn.px.sys.modular.member.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.entity.ActiveCommentEntity;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import cn.px.sys.modular.activity.service.ActiveCommentService;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integralWater.service.IntegralWaterService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.member.vo.MemberVo;
import cn.px.sys.modular.member.wrapper.MemberWrapper;
import cn.px.sys.modular.project.entity.ProjectCommentEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectCommentService;
import cn.px.sys.modular.project.service.ProjectEvaluateService;
import cn.px.sys.modular.project.service.ProjectUserSignService;
import cn.px.sys.modular.wx.service.AllUserService;
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
 * 用户信息管理(Member)表控制层
 *
 * @author
 * @since 2020-09-03 14:02:24
 */
@RestController
@RequestMapping("member")
@Api(value = "用户信息管理(Member)管理")
public class MemberController extends BaseController<MemberEntity, MemberService> {

    private static final String PREFIX = "/modular/member";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private MemberWrapper memberWrapper;
    @Resource
    private AllUserService allUserService;
    @Resource
    private ProjectUserSignService projectUserSignService;
    @Resource
    private ActivityUserSignService activityUserSignService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private IntegralWaterService integralWaterService;
    @Resource
    private ProjectEvaluateService projectEvaluateService;
    @Resource
    private ProjectCommentService projectCommentService;
    @Resource
    private ActiveCommentService activeCommentService;


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
    public Object query(ModelMap modelMap, MemberEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, MemberEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, MemberEntity params) {
        Assert.notNull(params.getId(), "ID");
       MemberEntity memberEntity = super.service.queryById(params.getId());
       //删除用户信息
        allUserService.delete(memberEntity.getUserId());
        //删除项目报名记录
       List<ProjectUserSignEntity> projectUserSignEntityList = projectUserSignService.getEntityByUser(memberEntity.getUserId());
        for (ProjectUserSignEntity projectUserSignEntity:projectUserSignEntityList) {
            projectUserSignService.delete(projectUserSignEntity.getId());
        }
        //活动报名记录
      List<ActivityUserSignEntity> activityUserSignEntityList =  activityUserSignService.getEntityByUser(memberEntity.getUserId());
        for (ActivityUserSignEntity activityUserSignEntity:activityUserSignEntityList) {
            activityUserSignService.delete(activityUserSignEntity.getId());
        }
        //积分记录
       List<IntegralRecordEntity> integralRecordEntityList = integralRecordService.getEntityByUser(memberEntity.getUserId());
        for (IntegralRecordEntity integralRecordEntity:integralRecordEntityList) {
            integralRecordService.delete(integralRecordEntity.getId());
        }
        //活动评论记录
       List<ActiveCommentEntity> activeCommentEntityList = activeCommentService.getListByUser(memberEntity.getUserId());
        for (ActiveCommentEntity activeCommentEntity:activeCommentEntityList) {
            activeCommentService.delete(activeCommentEntity.getId());
        }
        //项目评论记录
       List<ProjectCommentEntity> projectCommentEntityList = projectCommentService.getListByUser(memberEntity.getUserId());
        for (ProjectCommentEntity projectCommentEntity:projectCommentEntityList) {
            projectCommentService.delete(projectCommentEntity.getId());
        }

        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, MemberEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<MemberEntity> p = super.service.query(params, page);
        List<MemberEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("用户信息管理.xls", "Member"), MemberEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) String name,@RequestParam(required = false) String phone,@RequestParam(required = false) Long belongingCommunity,@RequestParam(required = false) Long belongingUnit,@RequestParam(required = false) Long expertise) {
        Long userId = LoginContextHolder.getContext().getUserId();
      Page<Map<String,Object>> member =  super.service.getListPage(name,phone,userId,belongingCommunity,belongingUnit,expertise);
      Page<MemberVo> pv =  memberWrapper.getVoPage(member);
      return super.setSuccessModelMap(pv);
    }

}