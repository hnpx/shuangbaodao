package cn.px.sys.modular.system.mapper;

import cn.px.sys.modular.system.entity.SysPrinterEntity;
import cn.px.base.core.BaseMapperImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (SysPrinter)表数据库访问层
 *
 * @author 
 * @since 2020-07-16 09:05:50
 */
public interface SysPrinterMapper extends BaseMapperImpl<SysPrinterEntity>{

    Page<Map<String, Object>> getSysPrinterList(@Param("page") Page page, @Param("name") String name, @Param("shopsName") String shopsName);


    public int updateStatus(@Param("id") long id, @Param("status") int status);


    public List<SysPrinterEntity> querySysPrinterBySid(@Param("sid") Long sid);
}