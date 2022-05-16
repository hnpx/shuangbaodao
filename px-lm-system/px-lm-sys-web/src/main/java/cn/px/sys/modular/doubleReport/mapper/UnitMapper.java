package cn.px.sys.modular.doubleReport.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.doubleReport.entity.UnitEntity;
import cn.px.sys.modular.doubleReport.vo.UnitListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单位管理(Unit)表数据库访问层
 *
 * @author
 * @since 2020-08-28 16:16:46
 */
public interface UnitMapper extends BaseMapperImpl<UnitEntity> {
    /**
     * 通过社区查询单位列表
     * @param belongingCommunity
     * @return
     */
    List<UnitEntity> getUnitEntityByCommunity(@Param("belongingCommunity") Long belongingCommunity);

    /**
     * 查询单位列表
     * @param page
     * @param name
     * @param cid
     * @return
     */
    Page<Map<String,Object>> getList(@Param("page") Page page,@Param("name") String name,
                                     @Param("cid") Long cid,@Param("contactPerson") String contactPerson,
                                     @Param("belongingCommunity") Long belongingCommunity,
                                     @Param("isUnit") Integer isUnit);

    /**
     * 通过手机号获取单位信息
     * @param phone
     * @return
     */
    public UnitEntity getUnitEntityByPhone(@Param("phone") String phone);


    /**
     * 通过用户id获取单位信息
     * @param userId
     * @return
     */
    public UnitEntity getUnitEntityByUserId(@Param("userId") Long userId);


    public UnitListVo getIntegral(@Param("cid") Long cid);
    public UnitListVo getIntegral1(@Param("userId") Long userId);

    public List<UnitEntity> getList1();


    /**
     * 通过手机号查询单位信息是否有匹配
     * @param phone
     * @return
     */
    public int countByPhone(@Param("phone") String phone);


    /**
     * 通过社区查询单位列表 (非社区)
     * @param belongingCommunity
     * @return
     */
    List<UnitEntity> getUnitEntityByCommunity1(@Param("belongingCommunity") Long belongingCommunity);




}