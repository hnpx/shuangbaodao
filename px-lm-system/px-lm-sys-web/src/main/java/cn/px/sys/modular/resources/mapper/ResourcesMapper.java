package cn.px.sys.modular.resources.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.resources.entity.ResourcesEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.Date;
import java.util.Map;

/**
 * 资源管理(Resources)表数据库访问层
 *
 * @author
 * @since 2020-09-04 12:32:44
 */
public interface ResourcesMapper extends BaseMapperImpl<ResourcesEntity> {
    /**
     * 资源列表
     * @return
     */
    public Page<Map<String,Object>> getPageList(@Param("page") Page page, @Param("date") Date date, @Param("resourcesClass") Long resourcesClass);

    /**
     * 资源列表后台
     * @return
     */
    public Page<Map<String,Object>> getPageListMan(@Param("page") Page page,@Param("name") String name,@Param("status") Integer status,
                                                   @Param("cid") Long cid,@Param("contactPerson") String contactPerson, @Param("phone") String phone);

    /**
     * 我的资源
     * @return
     */
    public Page<Map<String,Object>> getPageListMySelf(@Param("page") Page page,@Param("userId") Long userId);

}