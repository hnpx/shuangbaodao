package cn.px.sys.modular.system.mapper;

import cn.px.sys.modular.system.entity.SysFaqEntity;
import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.system.vo.SysFaqVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * (SysFaq)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-09 14:36:36
 */
public interface SysFaqMapper extends BaseMapperImpl<SysFaqEntity>{

	@Select("select name,answer content from sys_faq ${ew.customSqlSegment}")
	public List<Map<String,String>> getFaqVo(@Param(Constants.WRAPPER) QueryWrapper<SysFaqEntity> wrapper);
}
