package cn.px.base.support.cache;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface LockMapper extends BaseMapper<Lock> {

    void cleanExpiredLock();

}
