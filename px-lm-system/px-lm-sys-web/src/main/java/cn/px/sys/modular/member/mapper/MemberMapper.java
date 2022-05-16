package cn.px.sys.modular.member.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.member.entity.MemberEntity;
import cn.px.sys.modular.member.vo.MemberVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户信息管理(Member)表数据库访问层
 *
 * @author
 * @since 2020-09-03 14:02:37
 */
public interface MemberMapper extends BaseMapperImpl<MemberEntity> {
    /**
     * 通过手机号查询用户信息
     * @param userId
     * @return
     */
    public MemberEntity getMemberEntityByPhone(@Param("userId") Long userId);

    /**
     * 普通用户列表
     * @param page
     * @param name
     * @param phone
     * @return
     */
    public Page<Map<String,Object>> getListPage(@Param("page") Page page,@Param("name") String name,@Param("phone") String phone,
                                                @Param("cid") Long cid,@Param("belongingCommunity") Long belongingCommunity,@Param("belongingUnit") Long belongingUnit,
                                                @Param("expertise") Long expertise);

    /**
     * 通过用户id查询用户真实姓名
     * @param userId
     * @return
     */
    public MemberEntity getMemberEntityByUserId(@Param("userId") Long userId);

    /**
     * 根据社区id查询用户积分
     * @return
     */
    public MemberVo getIntegral(@Param("cid") Long cid);

    /**
     * 单位id查询用户积分
     * @return
     */
    public MemberVo getIntegral1(@Param("uid") Long uid);

    public List<MemberEntity> getMemberEntityByUnit(@Param("unit") Long unit);
}