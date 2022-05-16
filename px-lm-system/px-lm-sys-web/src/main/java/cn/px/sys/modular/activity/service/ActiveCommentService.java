package cn.px.sys.modular.activity.service;

import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.pojo.page.LayuiPageFactory;
import cn.px.sys.modular.activity.entity.ActiveCommentEntity;
import cn.px.sys.modular.activity.mapper.ActiveCommentMapper;
import cn.px.sys.modular.activity.vo.CommentVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 活动评价(ActiveComment)表服务实现类
 *
 * @author
 * @since 2020-08-31 11:09:08
 */
@Service("activeCommentService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "activeComment")
public class ActiveCommentService extends BaseServiceImpl<ActiveCommentEntity, ActiveCommentMapper> {

    /**
     * 通过用户id查询评论列表
     * @param page
     * @param activeId
     * @return
     */
    public Page<Map<String,Object>> getListByUserId(@Param("page") Integer page,@Param("pageSize") Integer pageSize, @Param("activeId") Long activeId){
        Page page1;
        if(pageSize != null){
           page1 = new Page(page,pageSize);
        }else {
            page1 = LayuiPageFactory.defaultPage();
        }

        return super.mapper.getListByUserId(page1,activeId);
    }

    public List<CommentVo> getListBy(@Param("activeId") Long activeId){
        return super.mapper.getListBy(activeId);
    }

   public List<ActiveCommentEntity> getListByUser( Long userId){
        return super.mapper.getListByUser(userId);
    }
}