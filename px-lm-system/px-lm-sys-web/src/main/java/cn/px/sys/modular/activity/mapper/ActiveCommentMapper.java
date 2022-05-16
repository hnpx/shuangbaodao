package cn.px.sys.modular.activity.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.activity.entity.ActiveCommentEntity;
import cn.px.sys.modular.activity.vo.CommentVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 活动评价(ActiveComment)表数据库访问层
 *
 * @author
 * @since 2020-08-31 11:09:21
 */
public interface ActiveCommentMapper extends BaseMapperImpl<ActiveCommentEntity> {
    /**
     * 通过用户id查询评论列表
     * @param page
     * @param activeId
     * @return
     */
    public Page<Map<String,Object>> getListByUserId(@Param("page") Page page, @Param("activeId") Long activeId);

    public List<CommentVo> getListBy(@Param("activeId") Long activeId);

    List<ActiveCommentEntity> getListByUser(@Param("userId") Long userId);

}