package cn.px.sys.modular.project.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.activity.vo.CommentVo;
import cn.px.sys.modular.project.entity.ProjectCommentEntity;
import cn.px.sys.modular.project.mapper.ProjectCommentMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 项目评价(ProjectComment)表服务实现类
 *
 * @author
 * @since 2020-09-04 09:05:53
 */
@Service("projectCommentService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "projectComment")
public class ProjectCommentService extends BaseServiceImpl<ProjectCommentEntity, ProjectCommentMapper> {

    /**
     * 根据项目id查询评价信息
     * @return
     */
    public List<CommentVo> getCommentVoList(@Param("projectId") Long projectId){
        return super.mapper.getCommentVoList(projectId);
    }


    /**
     * 查询项目评价列表
     * @param
     * @return
     */
    public Page<Map<String,Object>> getPageList(Integer page,Integer pageSize,Long projectId){
        Page page1;
        if(pageSize != null){
             page1 = new Page(page,pageSize);
        }else {
            page1 = LayuiPageFactory.defaultPage();
        }

        return super.mapper.getPageList(page1,projectId);
    }

    public List<ProjectCommentEntity> getListByUser( Long userId){
        return super.mapper.getListByUser(userId);
    }

}