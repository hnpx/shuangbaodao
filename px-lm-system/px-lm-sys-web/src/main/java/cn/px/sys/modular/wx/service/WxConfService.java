package cn.px.sys.modular.wx.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.BaseServiceImpl;
import cn.px.sys.modular.wx.entity.WxConfEntity;
import cn.px.sys.modular.wx.mapper.WxConfMapper;
import cn.px.sys.modular.wx.vo.WxConfVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (WxConf)表服务实现类
 *
 * @author
 * @since 2020-08-31 11:37:13
 */
@Service("wxConfService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE + "wxConf")
public class WxConfService extends BaseServiceImpl<WxConfEntity, WxConfMapper> {

    /**
     * 查询微信配置
     * @return
     */
    public WxConfVo getWxConfEntity(){
       List<WxConfEntity> wxConfEntityList = super.mapper.getWxConfEntity();
        WxConfEntity wxConfEntity = wxConfEntityList.get(0);
        WxConfVo wxConfVo = new WxConfVo();
        BeanUtil.copyProperties(wxConfEntity,wxConfVo);
        return wxConfVo;
    }

}