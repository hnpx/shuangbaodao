package cn.px.sys.modular.virtualProject.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.virtualProject.entity.VirtualProjectEntity;
import cn.px.sys.modular.virtualProject.vo.VirtualProjectExcelVo;
import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (VirtualProject)表数据库访问层
 *
 * @author
 * @since 2020-12-16 09:38:46
 */
public interface VirtualProjectMapper extends BaseMapperImpl<VirtualProjectEntity> {

    Page<Map<String,Object>> getList(@Param("page") Page page,@Param("name") String name,@Param("belongingCommunity") Long belongingCommunity,
                                     @Param("belongingUnit") Long belongingUnit,@Param("status") Integer status,@Param("companyClass") Long companyClass);


    List<VirtualProjectExcelVo> getListExcel(@Param("belongingCommunity") Long belongingCommunity);
}