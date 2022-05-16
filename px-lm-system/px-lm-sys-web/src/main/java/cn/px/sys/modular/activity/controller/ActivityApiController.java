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
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.service.ActivityService;
import cn.px.sys.modular.activity.vo.ActivityVo;
import cn.px.sys.modular.activity.wrapper.ActivityWrapper;
import cn.px.sys.modular.wx.controller.WxQr;
import cn.px.sys.modular.wx.service.WxConfService;
import cn.px.sys.modular.wx.vo.WxConfVo;
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
 * 活动管理(Activity)表控制层
 *
 * @author
 * @since 2020-08-27 15:09:54
 */
@RestController
@RequestMapping("api/activity")
@Api(value = "活动管理(Activity)管理")
public class ActivityApiController extends BaseController<ActivityEntity, ActivityService> {

    private static final String PREFIX = "/modular/activity";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private ActivityWrapper activityWrapper;
    @Autowired
    private SftpHelper sftpHelper;
    @Resource
    private WxConfService wxConfService;

    @ApiOperation("查询列表小程序端")
    @PutMapping("/read/homeListWx")
    @ResponseBody
    public Object query(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer pageSize,@RequestParam(required = false) Integer timeStatus) {
        Page<Map<String,Object>> activityInfo = super.service.getActivityWx(page,pageSize,timeStatus);
        Page<ActivityVo> pv =activityWrapper.getVoPage(activityInfo);
        return super.setSuccessModelMap(pv);
    }


    @ApiOperation("小程序首页列表")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(ModelMap modelMap, ActivityEntity params) {
     return super.setSuccessModelMap(super.service.getActiveAndProjectVo());
    }
}