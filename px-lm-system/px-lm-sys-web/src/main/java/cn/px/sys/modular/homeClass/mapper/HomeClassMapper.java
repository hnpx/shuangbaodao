package cn.px.sys.modular.homeClass.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.homeClass.entity.HomeClassEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (HomeClass)表数据库访问层
 *
 * @author
 * @since 2020-08-27 16:15:48
 */
public interface HomeClassMapper extends BaseMapperImpl<HomeClassEntity> {
    /**
     * 查询小区列表
     * @param page
     * @param name 小区名称
     * @param cname 社区名称
     * @param cid 社区id
     * @return
     */
    Page<Map<String,Object>> getHomeClass(@Param("page") Page page,@Param("name") String name,@Param("cname") String cname,@Param("cid") Long cid);

    /**
     * 通过社区id查询所有的小区
     * @param communityId
     * @return
     */
    List<HomeClassEntity> getHomeClassByCommunityId(@Param("communityId") Long communityId);


    Map<String,Object> HomeTotal();
}