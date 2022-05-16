package cn.px.base.oauth2.service;

import cn.px.base.pojo.page.LayuiPageInfo;
import cn.px.base.oauth2.entity.OauthUserInfo;
import cn.px.base.oauth2.model.params.OauthUserInfoParam;
import cn.px.base.oauth2.model.result.OauthUserInfoResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 第三方用户信息表 服务类
 * </p>
 *
 * @author PXHLT
 * @since 2019-06-09
 */
public interface OauthUserInfoService extends IService<OauthUserInfo> {

    /**
     * 新增
     *
     * @author PXHLT
     * @Date 2019-06-09
     */
    void add(OauthUserInfoParam param);

    /**
     * 删除
     *
     * @author PXHLT
     * @Date 2019-06-09
     */
    void delete(OauthUserInfoParam param);

    /**
     * 更新
     *
     * @author PXHLT
     * @Date 2019-06-09
     */
    void update(OauthUserInfoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author PXHLT
     * @Date 2019-06-09
     */
    OauthUserInfoResult findBySpec(OauthUserInfoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author PXHLT
     * @Date 2019-06-09
     */
    List<OauthUserInfoResult> findListBySpec(OauthUserInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author PXHLT
     * @Date 2019-06-09
     */
    LayuiPageInfo findPageBySpec(OauthUserInfoParam param);

    /**
     * 获取用户头像地址
     *
     * @author fengshuonan
     * @Date 2019-06-11 13:25
     */
    String getAvatarUrl(Long userId);
}
