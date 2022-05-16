package cn.px.sys.modular.wx.mapper;

import cn.px.base.core.BaseMapperImpl;
import cn.px.sys.modular.wx.entity.WxConfEntity;

import java.util.List;

/**
 * (WxConf)表数据库访问层
 *
 * @author
 * @since 2020-08-31 11:37:25
 */
public interface WxConfMapper extends BaseMapperImpl<WxConfEntity> {
    /**
     * 查询微信配置
     * @return
     */
    public List<WxConfEntity> getWxConfEntity();

}