package cn.px.sys.modular.communityClass.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.communityClass.entity.CommunityClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CommunityClass)表数据库访问层
 *
 * @author
 * @since 2020-08-27 16:03:07
 */
public interface CommunityClassMapper extends BaseMapperImpl<CommunityClassEntity> {
    /**
     * 根据用户id查询社区信息
     * @param userId
     * @return
     */
    public CommunityClassEntity getCommunityClassEntityByUserId(@Param("userId") Long userId);

    /**
     * 查询社区列表
     * @return
     */
    public List<CommunityClassEntity> getList();


}