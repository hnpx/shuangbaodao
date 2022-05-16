package cn.px.sys.modular.communityClass.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.auth.annotion.Permission;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.log.BussinessLog;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.core.constant.Const;
import cn.px.sys.core.constant.dictmap.UserDict;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.doubleReport.service.UnitService;
import cn.px.sys.modular.homeClass.service.HomeClassService;
import cn.px.sys.modular.system.entity.User;
import cn.px.sys.modular.system.model.UserDto;
import cn.px.sys.modular.system.service.UserService;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (CommunityClass)表控制层
 *
 * @author
 * @since 2020-08-27 16:02:55
 */
@RestController
@RequestMapping("communityClass")
@Api(value = "(CommunityClass)管理")
public class CommunityClassController extends BaseController<CommunityClassEntity, CommunityClassService> {

    private static final String PREFIX = "/modular/communityClass";
    protected static SuccessResponseData SUCCESS_TIP = new SuccessResponseData();
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

   @Resource
   private HomeClassService homeClassService;
   @Resource
   private UnitService unitService;

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
    public Object query(ModelMap modelMap, CommunityClassEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object update(ModelMap modelMap, CommunityClassEntity params) {
        //TODO 数据验证
        UserDto user = new UserDto();
        user.setAccount(params.getAccount());
        user.setPassword(params.getPassword());
        user.setName(params.getName());
        this.userService.addUser(user);
        User theUser =userService.getByAccount(user.getAccount());
        params.setUserId(theUser.getUserId());
        super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
        return SUCCESS_TIP;
    }
    @ApiOperation("修改")
    @PostMapping("/edit")
    @ResponseBody
    public Object edit(ModelMap modelMap, CommunityClassEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, CommunityClassEntity params) {
        Assert.notNull(params.getId(), "ID");
        int i = super.service.check(params.getId());
        if(i == 1){
            return super.setModelMap("400","该社区下绑定有信息请勿删除");
        }
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, CommunityClassEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<CommunityClassEntity> p = super.service.query(params, page);
        List<CommunityClassEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "CommunityClass"), CommunityClassEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    /**
     * 添加管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(@Valid UserDto user) {
        this.userService.addUser(user);
        return SUCCESS_TIP;
    }

    @ApiOperation("选择框查询")
    @PutMapping("/read/listById")
    @ResponseBody
    public Object query() {
      List<CommunityClassEntity> communityClassEntityList = new ArrayList<>();
     CommunityClassEntity communityClassEntity = super.service.getCommunityClassEntityByUserId(LoginContextHolder.getContext().getUserId());
     if(communityClassEntity != null){
         communityClassEntityList.add(communityClassEntity);
     }else {
         CommunityClassEntity communityClassEntity1 = new CommunityClassEntity();
         communityClassEntity1.setEnable(Constants.ENABLE_TRUE);
         communityClassEntityList = super.service.queryList(communityClassEntity1);
     }
     return super.setSuccessModelMap(communityClassEntityList);
    }



}