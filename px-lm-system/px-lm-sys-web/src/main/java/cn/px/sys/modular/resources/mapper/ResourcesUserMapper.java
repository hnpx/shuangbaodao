package cn.px.sys.modular.resources.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.resources.entity.ResourcesUserEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * (ResourcesUser)表数据库访问层
 *
 * @author
 * @since 2020-09-04 15:41:00
 */
public interface ResourcesUserMapper extends BaseMapperImpl<ResourcesUserEntity> {
    /**
     * 查询申请资源列表
     * @param page
     * @param name
     * @return
     */
    public Page<Map<String,Object>> getHomeList(@Param("page") Page page,@Param("name") String name,@Param("rid") Long rid);

}