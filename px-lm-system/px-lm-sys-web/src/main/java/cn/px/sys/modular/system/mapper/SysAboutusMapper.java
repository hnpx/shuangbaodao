package cn.px.sys.modular.system.mapper;

import cn.px.sys.modular.system.entity.SysAboutusEntity;
import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.system.entity.SysFaqEntity;
import cn.px.sys.modular.system.vo.SysAboutusVo;
import cn.px.sys.modular.system.vo.SysFaqVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (SysAboutus)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-09 14:35:08
 */
public interface SysAboutusMapper extends BaseMapperImpl<SysAboutusEntity>{

	@Select("select * from sys_aboutus ${ew.customSqlSegment}")
	public SysAboutusVo getAboutusVo(@Param(Constants.WRAPPER) QueryWrapper<SysAboutusEntity> wrapper);
}
