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
package cn.px.base.auth.service;

import cn.px.base.auth.model.LoginUser;

import java.util.List;

/**
 * Auth相关数据库的操作
 *
 * @author fengshuonan
 * @date 2016年12月5日 上午10:23:34
 */
public interface AuthService {

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return token
     */
    String login(String username, String password);

    /**
     * 微信授权登录
     * @param username
     * @param password
     * @return
     */
    String login1(String username, String password);

    /**
     * 微信授权登录
     * @param username
     * @return
     */
    String login1(String username);

    /**
     * 登录（直接用账号登录）
     *
     * @param username 账号
     * @return token
     */
    String login(String username);

    /**
     * 创建登录cookie
     */
    void addLoginCookie(String token);

    /**
     * 退出当前用户
     */
    void logout();

    /**
     * 退出
     */
    void logout(String token);

    /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    LoginUser user(String account);
    LoginUser user1(String account,Integer type);

    LoginUser user1(String account);

    /**
     * 获取权限列表通过角色id
     *
     * @param roleId 角色id
     */
    List<String> findPermissionsByRoleId(Long roleId);

    /**
     * 检查当前登录用户是否拥有指定的角色访问当
     *
     * @param roleNames 角色名称集合
     */
    boolean check(String[] roleNames);

    /**
     * 检查当前登录用户是否拥有当前请求的servlet的权限
     */
    boolean checkAll();


   public Object getToken(String token);

    public String loginWeb(String username,String passWord,Integer type);

}
