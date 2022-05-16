package cn.px.sys.modular.project.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.activity.vo.UserVo;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (ProjectUserSign)表数据库访问层
 *
 * @author
 * @since 2020-09-04 09:06:41
 */
public interface ProjectUserSignMapper extends BaseMapperImpl<ProjectUserSignEntity> {
    /**
     * 根据项目id查询此项目报名人数
     * @param projectId
     * @return
     */
    public List<ProjectUserSignEntity> getProjectUserSignEntityList(@Param("projectId") Long projectId);


    /**
     * 根据项目id查询此项目报名人
     * @param projectId
     * @return
     */
    public List<UserVo> getUserVo(@Param("projectId") Long projectId);

    /**
     * 查询项目报名
     * @param page
     * @param projectId
     * @return
     */
    public Page<Map<String,Object>> getPageList(@Param("page") Page page,@Param("projectId") Long projectId,
                                                @Param("unit") String unit,@Param("name") String name,@Param("phone") String phone
            ,@Param("type") Integer type,@Param("photo") Integer photo);

    /**
     * 查询项目报名
     * @param page
     * @param projectId
     * @return
     */
    public Page<Map<String,Object>> getPageList1(@Param("page") Page page, @Param("projectId") Long projectId, @Param("unit") Long unit, @Param("name") String name,
                                                 @Param("phone") String phone);

    /**
     * 我认领的项目列表
     * @param page
     * @param userId
     * @return
     */
    public Page<Map<String,Object>> getListByUserId(@Param("page") Page page, @Param("userId") Long userId, @Param("date") Date date, @Param("status") Integer status);

    /**
     * 通过项目id查询项目参与报名的用户
     * @param pid
     * @return
     */
    public List<ProjectUserSignEntity> getProjectEntityByPid(@Param("pid") Long pid);

    /**
     * 表现档案
     * @param page
     * @param userId
     * @return
     */
    Page<Map<String,Object>> getPerformance(@Param("page") Page page, @Param("userId") Long userId
            , @Param("projectName") String projectName, @Param("pid") Long pid, @Param("year") Integer year);

    void updateEnbale(ProjectUserSignEntity entity);

    /**
     * 通过用户id查询所有项目报名信息
     * @param userId
     * @return
     */
    List<ProjectUserSignEntity> getEntityByUser(@Param("userId") Long userId);


    /**
     * 通过项目id查询所有的报名信息
     * @param pid
     * @return
     */
    List<ProjectUserSignEntity> getEntityByPid(@Param("pid") Long pid);
    /**
     * 通过项目id和未认证未上传图片查询所有的报名信息
     * @param pid
     * @return
     */
    List<ProjectUserSignEntity> getProjectUserSignEntityByPid(@Param("pid") Long pid);


}