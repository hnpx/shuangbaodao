package cn.px.sys.modular.homeClass.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.homeClass.entity.HomeClassEntity;
import cn.px.sys.modular.homeClass.service.HomeClassService;
import cn.px.sys.modular.homeClass.vo.HomeClassVo;
import cn.px.sys.modular.homeClass.wrapper.HomeClassWrapper;
import cn.px.sys.modular.integral.service.IntegralRecordService;
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
 * (HomeClass)表控制层
 *
 * @author
 * @since 2020-08-27 16:15:35
 */
@RestController
@RequestMapping("homeClass")
@Api(value = "(HomeClass)管理")
public class HomeClassController extends BaseController<HomeClassEntity, HomeClassService> {

    private static final String PREFIX = "/modular/homeClass";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private HomeClassWrapper homeClassWrapper;
    @Resource
    private CommunityClassService communityClassService;
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
    public Object query(ModelMap modelMap, HomeClassEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, HomeClassEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, HomeClassEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, HomeClassEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<HomeClassEntity> p = super.service.query(params, page);
        List<HomeClassEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls", "HomeClass"), HomeClassEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }

    @ApiOperation("查询列表")
    @PutMapping("/read/homeList")
    @ResponseBody
    public Object query1(@RequestParam(required = false) String name,@RequestParam(required = false) String cname) {
     Long cid = null;

      CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId( LoginContextHolder.getContext().getUserId());
      if(communityClassEntity != null){
          cid = communityClassEntity.getId();
      }
     Page<Map<String,Object>> homeClassInfo = super.service.getHomeClass(name,cname,cid);
     Page<HomeClassVo> pv=homeClassWrapper.getVoPage(homeClassInfo);
     return super.setSuccessModelMap(pv);
    }


    @ApiOperation("通过社区id查询小区列表")
    @PutMapping("/read/homeListByCommunityId")
    @ResponseBody
    public Object query(@RequestParam(required = false) Long communityId) {

     return super.setSuccessModelMap(super.service.getHomeClassByCommunityId(communityId));
    }


    @ApiOperation("首页人数统计")
    @PutMapping("/read/hometotal")
    @ResponseBody
    public Object hometotal() {

        return super.service.HomeTotal();
    }
    @ApiOperation("使用的积分数量")
    @PutMapping("/read/getconsumption")
    @ResponseBody
    public Object getconsumption(){
     return super.setSuccessModelMap(integralRecordService.getconsumption());
    }

    @ApiOperation("商家的积分")
    @PutMapping("/read/getMerchantsIntegral")
    @ResponseBody
    public Object getMerchantsIntegral(){

        return super.setSuccessModelMap(integralRecordService.getMerchantsIntegral());
    }





}