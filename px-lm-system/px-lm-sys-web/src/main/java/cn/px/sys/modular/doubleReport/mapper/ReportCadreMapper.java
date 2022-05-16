package cn.px.sys.modular.doubleReport.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.doubleReport.entity.ReportCadreEntity;
import cn.px.sys.modular.doubleReport.vo.ReportCadreVo;
import cn.px.sys.modular.member.entity.MemberEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 党员干部信息管理(ReportCadre)表数据库访问层
 *
 * @author
 * @since 2020-08-28 14:11:02
 */
public interface ReportCadreMapper extends BaseMapperImpl<ReportCadreEntity> {
    /**
     * 通过手机号查询党员干部信息
     * @param phone
     * @return
     */
    public ReportCadreEntity getReportCadreEntityByPhone(@Param("phone") String phone);


    /**
     * 通过用户id查询党员干部信息
     * @param userId
     * @return
     */
    public ReportCadreEntity getReportCadreEntityByUserId(@Param("userId") Long userId);

    /**
     * 查询党员干部列表
     * @param page
     * @param name
     * @param phone
     * @param cid 社区id
     * @return
     */
    public Page<Map<String,Object>> getListHome(@Param("page") Page page,@Param("name") String name,@Param("phone") String phone,@Param("cid") Long cid,@Param("belongingCommunity") Long  belongingCommunity,@Param("belongingUnit") Long belongingUnit,@Param("expertise") Long expertise);

    /**
     * 导出党员干部列表
     * @param name
     * @param phone
     * @param cid
     * @return
     */
    public List<Map<String,Object>> exportListData( @Param("name") String name, @Param("phone") String phone, @Param("cid") String cid);


    public ReportCadreVo  getIntegral(@Param("cid") Long cid);

    public ReportCadreVo  getIntegral1(@Param("uid") Long uid);

    Page<Map<String,Object>> getListByUnitId(@Param("page")Page page,@Param("id")Long id,@Param("name") String name,@Param("phone") String phone);

    /**
     * 通过手机号查询党员干部信息是否有匹配
     * @param phone
     * @return
     */
    public int countByPhone(@Param("phone") String phone);

    public List<ReportCadreEntity> getReportCadreEntityByunit(@Param("unit") Long unit);

}