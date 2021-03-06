package cn.px.main.modular.demos.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.RandomUtil;
import cn.px.sys.modular.system.entity.User;
import cn.px.sys.modular.system.mapper.UserMapper;
import cn.px.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.mutidatasource.annotion.DataSource;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author PXHLT
 * @since 2018-12-07
 */
@Service
public class GunsDbService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserService userService;

    @DataSource(name = "master")
    public void gunsdb() {
        User user = new User();
        user.setAccount(RandomUtil.randomString(5));
        user.setPassword(RandomUtil.randomString(5));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setCreateUser(1L);
        user.setUpdateUser(1L);
        userService.save(user);
    }

}
