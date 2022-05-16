package cn.px.sys.modular.project.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.activity.vo.CommentVo;
import cn.px.sys.modular.project.entity.ProjectCommentEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目评价(ProjectComment)表数据库访问层
 *
 * @author
 * @since 2020-09-04 09:06:04
 */
public interface ProjectCommentMapper extends BaseMapperImpl<ProjectCommentEntity> {
    /**
     * 根据项目id查询评价信息
     * @return
     */
    public List<CommentVo> getCommentVoList(@Param("projectId") Long projectId);

    /**
     * 查询项目评价列表
     * @param page
     * @return
     */
    public Page<Map<String,Object>> getPageList(@Param("page") Page page,@Param("projectId") Long projectId);

    public List<ProjectCommentEntity> getListByUser(@Param("userId") Long userId);

}