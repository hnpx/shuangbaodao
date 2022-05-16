package cn.px.sys.modular.doubleReport.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.mapper.UnitMapper;
import cn.px.sys.modular.doubleReport.vo.UnitVo;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.service.MemberService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单位管理(Unit)表服务实现类
 *
 * @author
 * @since 2020-08-28 16:16:30
 */
@Service("unitService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "unit")
public class UnitService extends BaseServiceImpl<UnitEntity, UnitMapper> {
    @Resource
    private AllUserService allUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private ReportCadreService reportCadreService;
    @Resource
    private UnitService unitService;
    /**
     * 通过社区查询单位列表
     * @param belongingCommunity
     * @return
     */
   public List<UnitVo> getUnitEntityByCommunity(@Param("belongingCommunity") Long belongingCommunity){

        List<UnitEntity> unitEntityList = super.mapper.getUnitEntityByCommunity(belongingCommunity);
        List<UnitVo> unitVoList = new ArrayList<>();
        for (UnitEntity unitEntity:unitEntityList) {
            UnitVo unitVo = new UnitVo();
            BeanUtil.copyProperties(unitEntity,unitVo);
            unitVoList.add(unitVo);
        }
        return unitVoList;

    }

    /**
     * 查询单位列表
     * @param name
     * @param cid
     * @return
     */
   public Page<Map<String,Object>> getList(String name,Long cid,String contactPerson,Long belongingCommunity,Integer isUnit){
        Page page = LayuiPageFactory.defaultPage();
        return super.mapper.getList(page,name,cid,contactPerson,belongingCommunity,isUnit);
    }

    /**
     * 通过手机号获取单位信息
     * @param phone
     * @return
     */
    public UnitEntity getUnitEntityByPhone(@Param("phone") String phone){
        return super.mapper.getUnitEntityByPhone(phone);
    }


    /**
     * 通过社区查询单位列表 (非社区)
     * @param belongingCommunity
     * @return
     */
     public   List<UnitEntity> getUnitEntityByCommunity1(Long belongingCommunity){
        return super.mapper.getUnitEntityByCommunity1(belongingCommunity);
    }

    /**
     * 通过用户id 查询所属单位
     */
    public Long getUnit(Long user){

     AllUserEntity allUserEntity = allUserService.queryById(user);
     if(allUserEntity.getType() != null){
         Integer userType = allUserEntity.getType();
         //普通用户
         if (userType == 1){
             MemberEntity mEntity = new MemberEntity();
             mEntity.setUserId(user);
             MemberEntity memberEntity = memberService.selectOne(mEntity);
             if (memberEntity!=null){
                 return memberEntity.getBelongingUnit();
             }

         };

         //党员干部
         if (userType == 2) {
             ReportCadreEntity rEntity = new ReportCadreEntity();
             rEntity.setUserId(user);
             ReportCadreEntity reportCadreEntity = reportCadreService.selectOne(rEntity);
             if(reportCadreEntity != null){
                 return reportCadreEntity.getBelongingUnit();
             }

         };

         //单位
         if (userType == 3) {
             UnitEntity uEntity = new UnitEntity();
             uEntity.setUserId(user);
             UnitEntity unitEntity = unitService.selectOne(uEntity);
             if(unitEntity != null){
                 return unitEntity.getId();
             }
         }
     }
     return null;

    }

}