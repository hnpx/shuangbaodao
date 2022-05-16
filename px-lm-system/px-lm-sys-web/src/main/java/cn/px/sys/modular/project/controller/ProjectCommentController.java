package cn.px.sys.modular.project.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.activity.vo.CommentVo;
import cn.px.sys.modular.activity.wrapper.CommentWrapper;
import cn.px.sys.modular.activity.wrapper.UserWrapper;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.service.ReportCadreService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.project.entity.ProjectCommentEntity;
import cn.px.sys.modular.project.service.ProjectCommentService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
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
 * 项目评价(ProjectComment)表控制层
 *
 * @author
 * @since 2020-09-04 09:05:53
 */
@RestController
@RequestMapping("projectComment")
@Api(value = "项目评价(ProjectComment)管理")
public class ProjectCommentController extends BaseController<ProjectCommentEntity, ProjectCommentService> {

    private static final String PREFIX = "/modular/projectComment";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private CommentWrapper commentWrapper;
    @Resource
    private UserWrapper userWrapper;


    @Autowired
    private MemberService memberService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private ReportCadreService reportCadreService;

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
        return super.setSuccessModelMap(modelMap, super.service.queryById(id));
    }

    @ApiOperation("查询")
    @PutMapping("/read/list")
    @ResponseBody
    public Object query(ModelMap modelMap, ProjectCommentEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, ProjectCommentEntity params) {
        //TODO 数据验证
        params.setUserId(LoginContextHolder.getContext().getUserId());
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, ProjectCommentEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, ProjectCommentEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<ProjectCommentEntity> p = super.service.query(params, page);
        List<ProjectCommentEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("项目评价.xls", "ProjectComment"), ProjectCommentEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询评价列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) Long projectId) {
    Page<Map<String,Object>> projectComment =  super.service.getPageList(page,pageSize,projectId);
        List<Map<String,Object>> list = projectComment.getRecords();

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


       Page<CommentVo> pv = commentWrapper.getVoPage(projectComment);
       return super.setSuccessModelMap(pv);
    }

}