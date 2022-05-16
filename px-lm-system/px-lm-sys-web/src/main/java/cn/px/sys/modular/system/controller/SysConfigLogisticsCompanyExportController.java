package cn.px.sys.modular.system.controller;

import cn.px.sys.modular.system.entity.SysConfigLogisticsCompanyEntity;
import cn.px.sys.modular.system.service.SysConfigLogisticsCompanyService;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * (SysConfigLogisticsCompany)表导出excel控制层
 *
 * @author makejava
 * @since 2020-05-28 09:28:17
 */
@RestController
@RequestMapping("sysConfigLogisticsCompanyExport")
@ApiOperation(value = "(SysConfigLogisticsCompany)导出excel管理")
public class SysConfigLogisticsCompanyExportController extends BaseController<SysConfigLogisticsCompanyEntity,SysConfigLogisticsCompanyService>{

    @ApiOperation("导出excel")
    @PutMapping("/excel")
    public Object exportExcel(ModelMap modelMap, SysConfigLogisticsCompanyEntity params){
        Page<Long> page= LayuiPageFactory.defaultPage();
        page.setCurrent(1);
        page.setSize(Integer.MAX_VALUE);
        Pagination<SysConfigLogisticsCompanyEntity> p= super.service.query(params,page);
        List<SysConfigLogisticsCompanyEntity> data = p.getRecords();
        String fileName = ExportExcelUtil.doExportFile(new ExportParams(".xls","SysConfigLogisticsCompany"), SysConfigLogisticsCompanyEntity.class, data);
        return super.setSuccessModelMap(modelMap,fileName);
     }

}