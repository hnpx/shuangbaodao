package cn.px.sys.modular.integral.controller;

import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.core.BaseController;
import cn.px.sys.modular.integral.entity.IntegralRecordEntity;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.integral.vo.IntegralRecordApiVo;
import cn.px.sys.modular.integral.vo.RecordApiVo;
import cn.px.sys.modular.integral.wrapper.IntegralRecordApiWrapper;
import cn.px.sys.modular.integral.wrapper.IntegralRecordWrapper;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.sys.modular.wx.vo.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 积分管理(IntegralRecord)表控制层
 *
 * @author
 * @since 2020-09-07 09:35:58
 */
@RestController
@RequestMapping("api/integralRecord")
@Api(value = "积分管理(IntegralRecord)管理")
public class IntegralRecordApiController extends BaseController<IntegralRecordEntity, IntegralRecordService> {

    private static final String PREFIX = "/modular/integralRecord";
    @Autowired
    private HttpServletRequest request;
    @Resource
    private IntegralRecordWrapper integralRecordWrapper;

    @Resource
    private IntegralRecordApiWrapper integralRecordApiWrapper;


    @Autowired
    private AllUserService allUserService;



    @ApiOperation("查询积分流水")
    @GetMapping("/read/FindIntergralList")
    public Object FindIntergralList(@RequestParam("page") Integer page,
                                    @RequestParam(value = "pageSize") Integer pageSize){
        Page page1 = new Page(page,pageSize);
        Page<Map<String,Object>> integralRecord = super.service.FindIntergralList(page1);
        Page<IntegralRecordApiVo> pv =  integralRecordApiWrapper.getVoPage(integralRecord);
        pv.getRecords().forEach(a->{
            UserVo user =  allUserService.getName(a.getId());

            if (user!=null && user.getName()!=null){
                a.setNickName(user.getName());
            }


        });

        return setSuccessModelMap(pv);
    }


    @ApiOperation("查询个人排行")
    @GetMapping("/read/FindIntergralListByUser")
    public Object FindIntergralListByUser(
                                    @RequestParam("page") Integer page,
                                    @RequestParam(value = "pageSize") Integer pageSize){
        Page page1 = new Page(page,pageSize);
        RecordApiVo vo = new RecordApiVo();
        Page<Map<String,Object>> integralRecord = super.service.FindIntergralListByUser(page1);
        integralRecord.getRecords().forEach(a->{
            UserVo user = allUserService.getName((Long) a.get("id"));

            if (user!=null && user.getName()!=null){
                a.put("nickname",user.getName());
            }


        });
        vo.setPage(integralRecord);
        Long userId =  LoginContextHolder.getContext().getUserId();
        if (userId!=null){
            Map<String,Object> map = super.service.FindIntergralListByUserId(userId);
          vo.setMap(map);
        }
        vo.setCount(super.service.count());
        return setSuccessModelMap(vo);
    }


    @ApiOperation("查询组织排行")
    @GetMapping("/read/FindIntergralListByOrganization")
    public Object FindIntergralListByOrganization(

            @RequestParam("page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam("isUnit") Integer isUnit){
        Page page1 = new Page(page,pageSize);
        RecordApiVo vo = new RecordApiVo();
        Page<Map<String,Object>> integralRecord = super.service.FindIntergralListByOrganization(page1,isUnit);
        vo.setPage(integralRecord);
        Long userId =  LoginContextHolder.getContext().getUserId();
        if (userId!=null){
            UserVo user = allUserService.getName(userId);
            Map<String,Object> map = super.service.FindOrganizationIntergralListByUserId(user.getBelongingUnit());
            vo.setMap(map);
        }

        return setSuccessModelMap(vo);
    }


    @ApiOperation("查询社区排行")
    @GetMapping("/read/FindIntergralListByCommunity")
    public Object FindIntergralListByCommunity(
            @RequestParam("page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize){

        Page page1 = new Page(page,pageSize);
        RecordApiVo vo = new RecordApiVo();
        Page<Map<String,Object>> integralRecord = super.service.FindIntergralListByCommunity(page1);
        vo.setPage(integralRecord);
        Long userId =  LoginContextHolder.getContext().getUserId();
        if (userId!=null){
            //通过用户id获取社区id
            UserVo user = allUserService.getName(userId);
            Map<String,Object> map = super.service.FindCommunityByCommunityId(user.getBelongingCommunity());
            vo.setMap(map);
        }
        return setSuccessModelMap(vo);
    }


    @ApiOperation("查询组织平均分排行")
    @GetMapping("/read/FindIntergralAVGListByCommunity")
    public Object FindIntergralAVGListByCommunity(
            @RequestParam("page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize){
        Page page1 = new Page(page,pageSize);
        RecordApiVo vo = new RecordApiVo();
        Page<Map<String,Object>> integralRecord = super.service.FindIntergralAVGListByCommunity(page1);
        vo.setPage(integralRecord);
        Long userId =  LoginContextHolder.getContext().getUserId();
        if (userId!=null){
            //通过用户id获取单位id
            UserVo user = allUserService.getName(userId);
            Long id = user.getBelongingUnit();
            Map<String,Object> map = super.service.FindCommunityAVGByCommunityId(id);
            vo.setMap(map);
        }
        return setSuccessModelMap(vo);
    }






}