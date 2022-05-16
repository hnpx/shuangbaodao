package cn.px.sys.modular.member.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import cn.px.sys.modular.communityClass.service.CommunityClassService;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.mapper.MemberMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户信息管理(Member)表服务实现类
 *
 * @author
 * @since 2020-09-03 14:02:24
 */
@Service("memberService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "member")
public class MemberService extends BaseServiceImpl<MemberEntity, MemberMapper> {

    @Resource
    private CommunityClassService communityClassService;


    /**
     * 普通用户列表
     * @param
     * @param name
     * @param phone
     * @return
     */
    public Page<Map<String,Object>> getListPage(String name, String phone,Long userId,Long belongingCommunity,Long belongingUnit,Long expertise ){
        Page page = LayuiPageFactory.defaultPage();
        Long cid;
        if(userId == 1){
            cid = null;
        }else {
            CommunityClassEntity communityClassEntity = communityClassService.getCommunityClassEntityByUserId(userId);
            cid =  communityClassEntity.getId();
        }
        return super.mapper.getListPage(page,name,phone,cid,belongingCommunity,belongingUnit,expertise);
    }

    public List<MemberEntity> getMemberEntityByUnit( Long unit){
        return super.mapper.getMemberEntityByUnit(unit);
    }

}