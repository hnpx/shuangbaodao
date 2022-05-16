package cn.px.sys.modular.project.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.project.entity.ProjectEvaluateEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (ProjectEvaluate)表数据库访问层
 *
 * @author
 * @since 2020-09-28 11:03:34
 */
public interface ProjectEvaluateMapper extends BaseMapperImpl<ProjectEvaluateEntity> {
    /**
     *
     * @param pid
     * @return
     */
    public ProjectEvaluateEntity getByPid(@Param("pid") Long pid,@Param("userId") Long userId);


    public Page<Map<String,Object>> getHomeList(@Param("page") Page page,@Param("pid") Long pid);

    public int getCount(@Param("pid") Long pid,@Param("userId") Long userId);

    public List<ProjectEvaluateEntity> getListByUser(@Param("userId") Long userId);

}