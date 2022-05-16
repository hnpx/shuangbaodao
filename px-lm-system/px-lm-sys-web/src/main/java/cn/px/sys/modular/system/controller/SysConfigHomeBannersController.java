package cn.px.sys.modular.system.controller;

import cn.px.sys.modular.system.dto.SysConfigHomeBannersAddDTO;
import cn.px.sys.modular.system.entity.SysConfigHomeBannersEntity;
import cn.px.sys.modular.system.service.SysConfigHomeBannersService;
import cn.px.base.pojo.page.LayuiPageFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.support.Assert;
import cn.hutool.core.bean.BeanUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 首页广告(SysConfigHomeBanners)表控制层
 *
 * @author makejava
 * @since 2020-07-22 21:50:35
 */
@RestController
@RequestMapping("sysConfigHomeBanners")
@Api(value = "首页广告(SysConfigHomeBanners)管理",tags = "轮播图管理")
public class SysConfigHomeBannersController extends BaseController<SysConfigHomeBannersEntity,SysConfigHomeBannersService>{

	private static final String PREFIX = "/modular/sysConfigHomeBanners";
	@Autowired
	private HttpServletRequest request;


	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@GetMapping("/read/detail/{id}")
	@ResponseBody
	public Object selectOne(ModelMap modelMap,  @PathVariable("id") Long id) {
		return super.setSuccessModelMap(modelMap, super.service.queryById(id));
	}

	@ApiOperation("管理端查询轮播图")
	@PutMapping("/read/list")
	@ResponseBody
	public Object query(ModelMap modelMap, SysConfigHomeBannersEntity params) {
		Page<Long> page= LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
		return super.query(modelMap, params, page);
	}

	@ApiOperation("查询轮播图")
	@GetMapping("/getRotationChart")
	Object getRotationChart(){
		return setSuccessModelMap(service.getSysConfigHomeBannersVO());
	}

	@ApiOperation("添加轮播图")
	@PostMapping("/addRotationChart")
	Object addRotationChart(SysConfigHomeBannersAddDTO sysConfigHomeBannersAddDTO){
		service.addRotationChart(sysConfigHomeBannersAddDTO);
		return setSuccessModelMap();
	}

	@ApiOperation("更新")
	@PostMapping("/update")
	@ResponseBody
	public Object update(ModelMap modelMap, SysConfigHomeBannersEntity params) {
		//TODO 数据验证
		return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
	}

	@ApiOperation("删除")
	@PostMapping("/delete")
	@ResponseBody
	public Object del(ModelMap modelMap, SysConfigHomeBannersEntity params) {
		Assert.notNull(params.getId(), "ID");
		return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
	}

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, SysConfigHomeBannersEntity params){
        Page<Long> page= LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<SysConfigHomeBannersEntity> p= super.service.query(params,page);
        List<SysConfigHomeBannersEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("首页广告.xls","SysConfigHomeBanners"), SysConfigHomeBannersEntity.class, data);
        return super.setSuccessModelMap(modelMap,fileName);
     }

}