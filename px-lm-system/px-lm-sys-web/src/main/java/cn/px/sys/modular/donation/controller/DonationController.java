package cn.px.sys.modular.donation.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.donation.entity.DonationEntity;
import cn.px.sys.modular.donation.service.DonationService;
import cn.px.sys.modular.donation.vo.DonationVo;
import cn.px.sys.modular.donation.wrapper.DonationWrapper;
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
 * 捐赠管理(Donation)表控制层
 *
 * @author
 * @since 2020-09-02 18:02:03
 */
@RestController
@RequestMapping("donation")
@Api(value = "捐赠管理(Donation)管理")
public class DonationController extends BaseController<DonationEntity, DonationService> {

    private static final String PREFIX = "/modular/donation";
    @Autowired
    private HttpServletRequest request;
    @Resource

    private DonationWrapper donationWrapper;


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
    public Object query(ModelMap modelMap, DonationEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
        return super.query(modelMap, params, page);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, DonationEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, DonationEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, DonationEntity params) {
        Page<Long> page = LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<DonationEntity> p = super.service.query(params, page);
        List<DonationEntity> data = p.getRecords();
        //TODO
        String fileName = ExportExcelUtil.doExportFile(new ExportParams("捐赠管理.xls", "Donation"), DonationEntity.class, data);
        return super.setSuccessModelMap(modelMap, fileName);
    }


    @ApiOperation("捐赠记录")
    @PutMapping("/read/listHome")
    @ResponseBody
    public Object query(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Page<Map<String,Object>> donation = super.service.getHomeList(page,pageSize,userId);
        Page<DonationVo> pv=  donationWrapper.getVoPage(donation);
        return super.setSuccessModelMap(pv);
    }



}