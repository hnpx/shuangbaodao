/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.px.sys.core.auth;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.auth.exception.AuthException;
import cn.px.base.auth.exception.enums.AuthExceptionEnum;
import cn.px.base.auth.jwt.JwtTokenUtil;
import cn.px.base.auth.jwt.payload.JwtPayLoad;
import cn.px.base.auth.model.LoginUser;
import cn.px.base.auth.service.AuthService;
import cn.px.base.consts.ConstantsContext;
import cn.px.base.consts.UserTypeEnum;
import cn.px.base.tenant.context.DataBaseNameHolder;
import cn.px.base.tenant.context.TenantCodeHolder;
import cn.px.sys.core.auth.cache.SessionManager;
import cn.px.sys.core.constant.factory.ConstantFactory;
import cn.px.sys.core.constant.state.ManagerStatus;
import cn.px.sys.core.listener.ConfigListener;
import cn.px.sys.core.log.LogManager;
import cn.px.sys.core.log.factory.LogTaskFactory;
import cn.px.sys.core.util.SaltUtil;
import cn.px.sys.modular.system.entity.User;
import cn.px.sys.modular.system.factory.UserFactory;
import cn.px.sys.modular.system.mapper.MenuMapper;
import cn.px.sys.modular.system.mapper.UserMapper;
import cn.px.sys.modular.system.service.DictService;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static cn.px.base.consts.ConstantsContext.getJwtSecretExpireSec;
import static cn.px.base.consts.ConstantsContext.getTokenHeaderName;
import static cn.stylefeng.roses.core.util.HttpContext.getIp;

@Service("authService")
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private DictService dictService;

    @Autowired
    private SessionManager sessionManager;
    @Resource
    private AllUserService allUserService;


    public static AuthService me() {
        return SpringContextHolder.getBean(AuthService.class);
    }

    @Override
    public String login(String username, String password) {

        User user = userMapper.getByAccount(username);

        // 账号不存在
        if (null == user) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }

        //验证账号密码是否正确
        String requestMd5 = SaltUtil.md5Encrypt(password, user.getSalt());
        String dbMd5 = user.getPassword();
        if (dbMd5 == null || !dbMd5.equalsIgnoreCase(requestMd5)) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }

        return login(username);
    }

    @Override
    public String login1(String username, String password) {
        //使用微信登录，TODO 后期可能提供其他登录方式，可作为可配置项
        AllUserEntity allUser=this.allUserService.readByOpenid(username);
        // 账号不存在
        if (null == allUser) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }
        //TODO 使用微信认证，暂时不验证用户密码

        return login1(username);
    }
    @Override
    public String login1(String username) {

        AllUserEntity allUser=this.allUserService.readByOpenid(username);
        // 账号不存在
        if (null == allUser) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }

        // 账号被冻结
//        if (!assistan.getStatus().equals(ManagerStatus.OK.getCode())) {
//            throw new AuthException(AuthExceptionEnum.ACCOUNT_FREEZE_ERROR);
//        }

        //记录登录日志
//        LogManager.me().executeLog(LogTaskFactory.loginLog(user.getUserId(), getIp()));

        JwtPayLoad payLoad = new JwtPayLoad(allUser.getId(), allUser.getOpenid(), "xxxx");

        //创建token
        String token = JwtTokenUtil.generateToken(payLoad);

        //创建登录会话
        sessionManager.createSession(token, user1(username));

        //创建cookie
        addLoginCookie(token);

        return token;
    }

    @Override
    public String login(String username) {

        User user = userMapper.getByAccount(username);

        // 账号不存在
        if (null == user) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }

        // 账号被冻结
        if (!user.getStatus().equals(ManagerStatus.OK.getCode())) {
            throw new AuthException(AuthExceptionEnum.ACCOUNT_FREEZE_ERROR);
        }

        //记录登录日志
        LogManager.me().executeLog(LogTaskFactory.loginLog(user.getUserId(), getIp()));

        //TODO key的作用
        JwtPayLoad payLoad = new JwtPayLoad(user.getUserId(), user.getAccount(), "xxxx");

        //创建token
        String token = JwtTokenUtil.generateToken(payLoad);

        //创建登录会话
        sessionManager.createSession(token, user(username));

        //创建cookie
        addLoginCookie(token);

        return token;
    }

    @Override
    public void addLoginCookie(String token) {
        //创建cookie
        Cookie authorization = new Cookie(getTokenHeaderName(), token);
        authorization.setMaxAge(getJwtSecretExpireSec().intValue());
        authorization.setHttpOnly(true);
        authorization.setPath("/");
        HttpServletResponse response = HttpContext.getResponse();
        response.addCookie(authorization);
    }

    @Override
    public void logout() {
        String token = LoginContextHolder.getContext().getToken();
        logout(token);
    }

    @Override
    public void logout(String token) {

        //记录退出日志
        LogManager.me().executeLog(LogTaskFactory.exitLog(LoginContextHolder.getContext().getUser().getId(), getIp()));

        //删除Auth相关cookies
        Cookie[] cookies = HttpContext.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String tokenHeader = getTokenHeaderName();
                if (tokenHeader.equalsIgnoreCase(cookie.getName())) {
                    Cookie temp = new Cookie(cookie.getName(), "");
                    temp.setMaxAge(0);
                    temp.setPath("/");
                    HttpContext.getResponse().addCookie(temp);
                }
            }
        }

        //删除会话
        sessionManager.removeSession(token);
    }

    @Override
    public LoginUser user(String account) {

        User user = userMapper.getByAccount(account);

        LoginUser loginUser = UserFactory.createLoginUser(user);

        loginUser.setUserType(UserTypeEnum.ADMIN.getValue());
        //用户角色数组
        Long[] roleArray = Convert.toLongArray(user.getRoleId());

        //如果角色是空就直接返回
        if (roleArray == null || roleArray.length == 0) {
            return loginUser;
        }

        //获取用户角色列表
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleTipList = new ArrayList<>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
            roleTipList.add(ConstantFactory.me().getSingleRoleTip(roleId));
        }
        loginUser.setRoleList(roleList);
        loginUser.setRoleNames(roleNameList);
        loginUser.setRoleTips(roleTipList);

        //根据角色获取系统的类型
        List<String> systemTypes = this.menuMapper.getMenusTypesByRoleIds(roleList);

        //通过字典编码
        List<Map<String, Object>> dictsByCodes = dictService.getDictsByCodes(systemTypes);
        loginUser.setSystemTypes(dictsByCodes);

        //设置权限列表
        Set<String> permissionSet = new HashSet<>();
        for (Long roleId : roleList) {
            List<String> permissions = this.findPermissionsByRoleId(roleId);
            if (permissions != null) {
                for (String permission : permissions) {
                    if (ToolUtil.isNotEmpty(permission)) {
                        permissionSet.add(permission);
                    }
                }
            }
        }
        loginUser.setPermissions(permissionSet);

        //如果开启了多租户功能，则设置当前登录用户的租户标识
        if (ConstantsContext.getTenantOpen()) {
            String tenantCode = TenantCodeHolder.get();
            String dataBaseName = DataBaseNameHolder.get();
            if (ToolUtil.isNotEmpty(tenantCode) && ToolUtil.isNotEmpty(dataBaseName)) {
                loginUser.setTenantCode(tenantCode);
                loginUser.setTenantDataSourceName(dataBaseName);
            }

            //注意，这里remove不代表所有情况，在aop remove
            TenantCodeHolder.remove();
            DataBaseNameHolder.remove();
        }

        return loginUser;
    }


    @Override
    public LoginUser user1(String account,Integer type) {

        User user = userMapper.getByAccount(account);


        LoginUser loginUser = UserFactory.createLoginUser(user);

        loginUser.setUserType(UserTypeEnum.ADMIN.getValue());
        //用户角色数组
        Long[] roleArray = Convert.toLongArray(1);

        //如果角色是空就直接返回
        if (roleArray == null || roleArray.length == 0) {
            return loginUser;
        }

        //获取用户角色列表
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleTipList = new ArrayList<>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
            roleTipList.add(ConstantFactory.me().getSingleRoleTip(roleId));
        }
        loginUser.setRoleList(roleList);
        loginUser.setRoleNames(roleNameList);
        loginUser.setRoleTips(roleTipList);

        //根据角色获取系统的类型
        List<String> systemTypes = this.menuMapper.getMenusTypesByRoleIds(roleList);

        //通过字典编码
        List<Map<String, Object>> dictsByCodes = dictService.getDictsByCodes(systemTypes);
        loginUser.setSystemTypes(dictsByCodes);

        //设置权限列表
        Set<String> permissionSet = new HashSet<>();
        for (Long roleId : roleList) {
            List<String> permissions = this.findPermissionsByRoleId(roleId);
            if (permissions != null) {
                for (String permission : permissions) {
                    if (ToolUtil.isNotEmpty(permission)) {
                        permissionSet.add(permission);
                    }
                }
            }
        }
        loginUser.setPermissions(permissionSet);

        //如果开启了多租户功能，则设置当前登录用户的租户标识
       /* if (ConstantsContext.getTenantOpen()) {
            String tenantCode = TenantCodeHolder.get();
            String dataBaseName = DataBaseNameHolder.get();
            if (ToolUtil.isNotEmpty(tenantCode) && ToolUtil.isNotEmpty(dataBaseName)) {
                loginUser.setTenantCode(tenantCode);
                loginUser.setTenantDataSourceName(dataBaseName);
            }

            //注意，这里remove不代表所有情况，在aop remove
            TenantCodeHolder.remove();
            DataBaseNameHolder.remove();
        }*/

        return loginUser;
    }



    @Override
    public List<String> findPermissionsByRoleId(Long roleId) {
        return menuMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public boolean check(String[] roleNames) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        if (null == user) {
            return false;
        }
        ArrayList<String> objects = CollectionUtil.newArrayList(roleNames);
        String join = CollectionUtil.join(objects, ",");
        if (LoginContextHolder.getContext().hasAnyRoles(join)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAll() {
        HttpServletRequest request = HttpContext.getRequest();
        LoginUser user = LoginContextHolder.getContext().getUser();
        if (null == user) {
            return false;
        }
        String requestURI = request.getRequestURI().replaceFirst(ConfigListener.getConf().get("contextPath"), "");
        String[] str = requestURI.split("/");
        if (str.length > 3) {
            requestURI = "/" + str[1] + "/" + str[2];
        }
        if (LoginContextHolder.getContext().hasPermission(requestURI)) {
            return true;
        }
        return false;
    }

    @Override
    public Object getToken(String token) {
        return null;
    }
    @Override
    public String loginWeb(String username,String passWord,Integer type) {
        String pwd = null;
        String requestMd5 = null;
        Long userId = null;
        Long storeId = null;
        String storeName = null;
        String token1 = null;
        switch (type) {

            case 1:
                  //
                    break;

            case 2:

                break;

            case 3:

                break;

        }
        JwtPayLoad payLoad = new JwtPayLoad(storeId, storeName, "xxxx");
        //TODO key的作用
        String token = JwtTokenUtil.generateToken(payLoad);

        LoginUser loginUser = new LoginUser();

        loginUser.setId(userId);
        loginUser.setAccount(storeName);
      /*  loginUser.setDeptId(user.getDeptId());
        loginUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptId()));*/
     /*   loginUser.setName(user.getName());
        loginUser.setEmail(user.getEmail());
*/
      //  loginUser.setAvatar("/api/system/preview/" + loginUser.getAvatar());

        //创建登录会话
       // sessionManager.createSession(token,loginUser);

        //创建cookie
        addLoginCookie(token);

        return token;
    }




    @Override
    public LoginUser user1(String account) {

        AllUserEntity member = this.allUserService.readByOpenid(account);

        LoginUser loginUser = createLoginUser(member);

        //TODO 1、获取店员的角色信息`

        //TODO 2、初始化店员的权限



        //如果开启了多租户功能，则设置当前登录用户的租户标识
        if (ConstantsContext.getTenantOpen()) {
            String tenantCode = TenantCodeHolder.get();
            String dataBaseName = DataBaseNameHolder.get();
            if (ToolUtil.isNotEmpty(tenantCode) && ToolUtil.isNotEmpty(dataBaseName)) {
                loginUser.setTenantCode(tenantCode);
                loginUser.setTenantDataSourceName(dataBaseName);
            }

            //注意，这里remove不代表所有情况，在aop remove
            TenantCodeHolder.remove();
            DataBaseNameHolder.remove();
        }

        return loginUser;
    }


    /**
     * 通过用户表的信息创建一个登录用户
     */
    public static LoginUser createLoginUser(AllUserEntity user) {
        LoginUser loginUser = new LoginUser();

        if (user == null) {
            return loginUser;
        }
        loginUser.setId(user.getId());
        loginUser.setAccount(user.getOpenid());
        loginUser.setName(user.getNickname());
        loginUser.setUserType(user.getType());
        return loginUser;
    }

}
