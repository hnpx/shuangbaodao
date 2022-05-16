package cn.px.sys.modular.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.util.UUIDUtil;
import cn.px.sys.modular.system.dto.SysConfigHomeBannersAddDTO;
import cn.px.sys.modular.system.entity.SysConfigHomeBannersEntity;
import cn.px.sys.modular.system.mapper.SysConfigHomeBannersMapper;
import cn.px.sys.modular.system.vo.SysConfigHomeBannersVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import cn.px.base.core.BaseServiceImpl;
import cn.px.base.Constants;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * 首页广告(SysConfigHomeBanners)表服务实现类
 *
 * @author makejava
 * @since 2020-07-22 21:50:35
 */
@Service("sysConfigHomeBannersService")
@CacheConfig(cacheNames = Constants.ENTITY_CACHE_NAMESPACE+"sysConfigHomeBanners")
public class SysConfigHomeBannersService extends BaseServiceImpl<SysConfigHomeBannersEntity,SysConfigHomeBannersMapper> {
    public List<SysConfigHomeBannersEntity> getSysConfigHomeBannersVO(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("enable",1);
        queryWrapper.eq("status",1);
        List<SysConfigHomeBannersEntity> sysConfigHomeBannersEntityList=mapper.selectList(queryWrapper);

        return sysConfigHomeBannersEntityList;
    }

    public void addRotationChart(SysConfigHomeBannersAddDTO sysConfigHomeBannersAddDTO) {
        SysConfigHomeBannersEntity sysConfigHomeBannersEntity=new SysConfigHomeBannersEntity();
        BeanUtil.copyProperties(sysConfigHomeBannersAddDTO,sysConfigHomeBannersEntity);
        sysConfigHomeBannersEntity.setId(UUIDUtil.getOrderIdByUUId());
        mapper.insert(sysConfigHomeBannersEntity);
    }
/*
    public Object addRotationChart() {

        mapper.insert(sysConfigHomeBannersEntity);
    }*/
}