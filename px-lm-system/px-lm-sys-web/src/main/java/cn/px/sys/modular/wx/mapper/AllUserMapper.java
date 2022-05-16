package cn.px.sys.modular.wx.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (AllUser)表数据库访问层
 *
 * @author
 * @since 2020-08-29 11:03:47
 */
public interface AllUserMapper extends BaseMapperImpl<AllUserEntity> {
    /**
     * 通过openid查询用户
     * @param openid
     * @return
     */
    public AllUserEntity readByOpenid(@Param("openid") String openid);

    /**
     * 查询所有的党员干部信息
     * @return
     */
   public Page<Map<String,Object>>  getAllUserEntityByType(@Param("page") Page page);



    /**
     * 查询所有的党员干部信息
     * @return
     */
    public Page<Map<String,Object>>  getAllUserEntityByType2(@Param("page") Page page,@Param("unit") Long unit);

    /**
     * 查询所有的党员干部信息
     * @return
     */
    public List<AllUserEntity>  getAllUserEntityByType1();

    /**
     * 查询真实姓名
     * @return
     */
    public AllUserEntity getNameByUserId(@Param("userId") Long userId);

    /**
     * 查询需要报名用户
     * @param
     * @param page
     * @return
     *
     */
    public Page<Map<String,Object>> getListUser(@Param("page") Page page,@Param("userIds") String userIds,@Param("nickname") String nickname,@Param("userType") Integer userType,
                                                @Param("uid") Long uid);

    public Page<Map<String,Object>> getListUser1(@Param("page") Page page,@Param("userIds") String userIds,@Param("name") String name,@Param("userType") Integer userType,
                                                @Param("phone") String phone,@Param("unit") String unit );

    /**
     *
     * @param activeId
     * @return
     */
    public List<AllUserEntity> getUserIds(@Param("activeId") Long activeId);

    /**
     *
     * @param
     * @return
     */
    public List<AllUserEntity> getUserIds1(@Param("pid") Long pid);


    void updatePoints(AllUserEntity entity);

    AllUserEntity getAllUserEntityById(@Param("id") Long id);



}