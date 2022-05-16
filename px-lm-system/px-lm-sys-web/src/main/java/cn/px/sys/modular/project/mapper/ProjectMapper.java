package cn.px.sys.modular.project.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.project.entity.ProjectEntity;
import cn.px.sys.modular.project.vo.ProVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目管理(Project)表数据库访问层
 *
 * @author
 * @since 2020-09-02 20:20:20
 */
public interface ProjectMapper extends BaseMapperImpl<ProjectEntity> {
    /**
     * 查询项目列表
     * @param page
     * @param name
     * @return
     */
    Page<Map<String,Object>> getListPage(@Param("page") Page page,@Param("name") String name,@Param("cid") Long cid,@Param("contactPerson") String contactPerson,
                                         @Param("phone") String phone,@Param("timeStatus") Integer timeStatus,@Param("status") Integer status,@Param("companyClass") Long companyClass);

    /**
     * 查询项目列表cid
     * @param page
     * @param cid   项目分类id
     * @return
     */
    Page<Map<String,Object>> getListPage1(@Param("page") Page page,@Param("cid") Long cid,@Param("date") Date date,@Param("timeType") Integer timeType);

    /**
     * 小程序端首页
     * @return
     */
    public List<ProVo> getProVo(@Param("date") Date date);

    /**
     * 项目没有开启签到  每天凌晨自动加积分
     * @return
     */
    public  List<ProjectEntity> getList();

    void insertEntity(ProjectEntity entity);

    /**
     * 项目清单列表
     * @param page
     * @param classId
     * @return
     */
    Page<Map<String,Object>> getPageList(@Param("page") Page page,@Param("classId") Long classId);



}