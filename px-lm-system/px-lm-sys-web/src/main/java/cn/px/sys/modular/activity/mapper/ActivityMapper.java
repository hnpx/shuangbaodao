package cn.px.sys.modular.activity.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.activity.entity.ActivityEntity;
import cn.px.sys.modular.activity.vo.ActiveVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 活动管理(Activity)表数据库访问层
 *
 * @author
 * @since 2020-08-27 15:10:06
 */
public interface ActivityMapper extends BaseMapperImpl<ActivityEntity> {
    /**
     * 活动列表
     * @param page
     * @param name
     * @param
     * @return
     */
    Page<Map<String,Object>> getActivity(@Param("page") Page page, @Param("name") String name,
                                         @Param("date") Date date, @Param("status") Integer status, @Param("cid") Long cid,
                                         @Param("contactPerson") String contactPerson, @Param("phone") String phone,@Param("timeStatus") Integer timeStatus);

    /**
     * 活动列表小程序端
     * @param page
     * @return
     */
    Page<Map<String,Object>> getActivityWx(@Param("page") Page page, @Param("date") Date date,@Param("timeStatus") Integer timeStatus);

    /**
     * 小程序首页列表
     * @return
     */
    public List<ActiveVo> getActiveVo(@Param("date") Date date);

    /**
     * 消息信息
     * @param page
     * @return
     */
    Page<Map<String,Object>> getNote(@Param("page") Page page,@Param("userId") Long userId);

    /**
     * 消息信息
     * @param page
     * @return
     */
    Page<Map<String,Object>> getNote1(@Param("page") Page page);


    /**
     * 查询不签到所有的活动
     * @return
     */
    public List<ActivityEntity> getList();

    /**
     * 审批
     * @param page
     * @return
     */
    Page<Map<String,Object>> getApply(@Param("page") Page page,@Param("cid") Long cid);




}