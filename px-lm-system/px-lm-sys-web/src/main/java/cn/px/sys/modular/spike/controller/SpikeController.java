package cn.px.sys.modular.spike.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.base.support.Assert;
import cn.px.base.support.Pagination;
import cn.px.base.util.ExportExcelUtil;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.spike.dto.SpikeAddDTO;
import cn.px.sys.modular.spike.entity.SpikeEntity;
import cn.px.sys.modular.spike.service.ProductService;
import cn.px.sys.modular.spike.service.SpikeService;
import cn.px.sys.modular.spike.vo.SpikeQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 限时秒杀(Spike)表控制层
 *
 * @author makejava
 * @since 2020-09-07 16:03:35
 */
@RestController
@Api(value = "限时秒杀(Spike)管理", tags = "限时秒杀(Spike)管理")
public class SpikeController extends BaseController<SpikeEntity, SpikeService> {

    private static final String PREFIX = "/modular/spike";
    @Autowired
    private HttpServletRequest request;

    @Autowired
    ProductService productService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("spike/read/detail/{id}")
    @ResponseBody
    public Object selectOne(ModelMap modelMap, @PathVariable("id") Long id) {
        return super.setSuccessModelMap(modelMap, super.service.queryById(id));
    }



    @ApiOperation("查询限时秒杀")
    @PutMapping("api/spike/read/timeLimitedSecondKill")
    public Object timeLimitedSecondKill(Long page, Long limit) {
        return setSuccessModelMap(service.timeLimitedSecondKill(page,limit));
    }


    @ApiOperation("查询限时秒杀后台")
    @PutMapping("api/spike/read/timeLimitedSecondKill1")
    public Object timeLimitedSecondKill1(@RequestParam(required = false) Integer timeStatus,@RequestParam(required = false) String name ) {
        Page<SpikeQueryVO> ob =  service.timeLimitedSecondKill1(timeStatus,name);
        ob.getRecords().forEach(a->{
            if(new Date(a.getStratTime().toString()).compareTo(new Date())==1){
               a.setTimeStatus(1); //未开始
            } else if(new Date(a.getStratTime().toString()).compareTo(new Date())==-1 && new Date(a.getEndTime().toString()).compareTo(new Date())==1){
                a.setTimeStatus(2); //进行中
            }else if(new Date(a.getEndTime().toString()).compareTo(new Date())==-1){
                a.setTimeStatus(3); //已结束
            }
        });
        return setSuccessModelMap(ob);
    }

    @ApiOperation("添加秒殺商品")
    @PostMapping("spike/update")
    @Transactional
    public Object add(SpikeAddDTO spikeAddDTO) {
        SpikeEntity spikeEntity=new SpikeEntity();
        BeanUtil.copyProperties(spikeAddDTO,spikeEntity);
        SpikeEntity spikeEntity1 = super.service.check(spikeAddDTO.getProductId());
        if(spikeEntity1 == null)
        {
            SpikeEntity spikeEntity2 = super.service.check1(spikeAddDTO.getProductId());
            if(spikeEntity2 == null){
                service.update(spikeEntity);
                return setSuccessModelMap();
            }else {
                return super.setModelMap("400","此商品正处于秒杀或者秒杀未开始状态,此时不能再添加为秒杀");
            }
        }else {
            spikeEntity1.setEnable(Constants.ENABLE_FALSE);
            service.update(spikeEntity1);
        }
        service.update(spikeEntity);
        return setSuccessModelMap();
    }
    @ApiOperation("修改")
    @PostMapping("/update")
    @ResponseBody
    public Object update(ModelMap modelMap, SpikeEntity params) {
        //TODO 数据验证
        return super.update(modelMap, params, LoginContextHolder.getContext().getUserId());
    }
    @ApiOperation("删除")
    @PostMapping("spike/delete")
    @ResponseBody
    public Object del(ModelMap modelMap, SpikeEntity params) {
        Assert.notNull(params.getId(), "ID");
        return super.del(modelMap, params, LoginContextHolder.getContext().getUserId());
    }

    @ApiOperation("查询秒杀活动详情")
    @GetMapping("/getSeckillActivityDetails")
    Object getSeckillActivityDetails(Long productId){
        return setSuccessModelMap(service.getSeckillActivityDetails(productId));

    }



}