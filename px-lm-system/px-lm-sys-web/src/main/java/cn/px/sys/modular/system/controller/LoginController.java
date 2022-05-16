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
package cn.px.sys.modular.system.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.thread.ThreadUtil;
import cn.px.base.auth.context.LoginContextHolder;
import cn.px.base.auth.model.LoginUser;
import cn.px.base.auth.service.AuthService;
import cn.px.base.consts.ConstantsContext;
import cn.px.base.consts.HeaderConstant;
import cn.px.base.core.util.RequestHeaderUtil;
import cn.px.base.exception.LoginException;
import cn.px.base.support.http.HttpCode;
import cn.px.base.tenant.context.DataBaseNameHolder;
import cn.px.base.tenant.context.TenantCodeHolder;
import cn.px.base.tenant.entity.TenantInfo;
import cn.px.base.tenant.service.TenantInfoService;
import cn.px.sys.core.auth.cache.SessionManager;
import cn.px.sys.core.exception.InvalidKaptchaException;
import cn.px.sys.core.exception.enums.BizExceptionEnum;
import cn.px.sys.core.wx.AesCbcUtil;
import cn.px.sys.core.wx.WxMaConfiguration;
import cn.px.sys.modular.integral.service.IntegralRecordService;
import cn.px.sys.modular.system.entity.WxConfigEntity;
import cn.px.sys.modular.system.service.UserService;
import cn.px.sys.modular.system.vo.LoginUserVo;
import cn.px.sys.modular.wx.entity.AllUserEntity;
import cn.px.sys.modular.wx.service.AllUserService;
import cn.px.sys.modular.wx.service.WxConfService;
import cn.px.sys.modular.wx.vo.WxConfVo;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.mutidatasource.DataSourceContextHolder;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.google.code.kaptcha.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@Slf4j
public class LoginController extends BaseController {
    protected Logger logger = LogManager.getLogger();
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private HttpServletRequest request;
    @Resource
    private AllUserService allUserService;
    @Resource
    private WxConfService wxConfService;
    @Resource
    private IntegralRecordService integralRecordService;

    /**
     * 获取当前用户的信息
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/read/current")
    @ResponseBody
    @ApiOperation("获取当前用户信息")
    public Object currentInfo(ModelMap modelMap) {
        //判断用户是否登录
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //用户信息为空，提示账号没分配角色登录不进去
            if (userIndexInfo == null) {
                Map<String, Object> result = new HashMap<>();
                result.put("httpCode", HttpCode.UNAUTHORIZED);
                return result;
            } else {
                return userIndexInfo;
            }
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("httpCode", HttpCode.UNAUTHORIZED);
            return result;
        }
    }

    /**
     * 跳转到登陆页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/show/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        return "/login.html";
    }

    /**
     * 跳转到主页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        //判断用户是否登录
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //用户信息为空，提示账号没分配角色登录不进去
            if (userIndexInfo == null) {
                model.addAttribute("tips", "该用户没有角色，无法登陆");
                return "/login.html";
            } else {
                model.addAllAttributes(userIndexInfo);
                return "/index.html";
            }

        } else {
            return "/login.html";
        }
    }

    /**
     * 跳转到登录页面
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (LoginContextHolder.getContext().hasLogin()) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }


    /**
     * 点击登录执行的动作
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("后台登录")
    public ResponseData loginVali(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        String username = super.getPara("username");
        String password = super.getPara("password");

        if (ToolUtil.isOneEmpty(username, password)) {
            throw new RequestEmptyException("账号或密码为空！");
        }

        //如果系统开启了多租户开关，则添加租户的临时缓存
        if (ConstantsContext.getTenantOpen()) {
            String tenantCode = super.getPara("tenantCode");
            if (ToolUtil.isNotEmpty(tenantCode)) {

                //从spring容器中获取service，如果没开多租户功能，没引入相关包，这里会报错
                TenantInfoService tenantInfoService = null;
                try {
                    tenantInfoService = SpringContextHolder.getBean(TenantInfoService.class);
                } catch (Exception e) {
                    throw new ServiceException(500, "找不到多租户service，请检查是否引入guns-tenant模块！");
                }

                //获取租户信息
                TenantInfo tenantInfo = tenantInfoService.getByCode(tenantCode);
                if (tenantInfo != null) {
                    String dbName = tenantInfo.getDbName();

                    //添加临时变量（注意销毁）
                    TenantCodeHolder.put(tenantCode);
                    DataBaseNameHolder.put(dbName);
                    DataSourceContextHolder.setDataSourceType(dbName);
                } else {
                    throw new ServiceException(BizExceptionEnum.NO_TENANT_ERROR);
                }
            }
        }

        //验证验证码是否正确
        if (ConstantsContext.getKaptchaOpen()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        //登录并创建token
        String token = authService.login(username, password);
        return new SuccessResponseData(token);
    }

    /**
     * 利用已有的token进行登录（一般用在oauth登录）
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/sysTokenLogin")
    public String sysTokenLogin(@RequestParam(value = "token", required = false) String token, Model model) {
        if (ToolUtil.isNotEmpty(token)) {

            //从session获取用户信息，没有就是token无效
            LoginUser user = sessionManager.getSession(token);
            if (user == null) {
                return "/login.html";
            }

            //创建当前登录上下文
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //渲染首页需要的用户信息
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();
            if (userIndexInfo == null) {
                model.addAttribute("tips", "该用户没有角色，无法登陆！");
                return "/login.html";
            } else {
                model.addAllAttributes(userIndexInfo);
            }

            //创建cookie
            authService.addLoginCookie(token);

            return "/index.html";
        } else {
            model.addAttribute("tips", "token请求为空，无法登录！");
            return "/login.html";
        }
    }

    /**
     * 退出登录
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public ResponseData logOut() {
        authService.logout();
        return new SuccessResponseData();
    }


    /**
     * 点击登录执行的动作
     */
    @GetMapping(value = "/login/wx")
    @ApiOperation("用户登录服务")
    @ResponseBody
    public Object loginVali(@RequestParam(value = "code", required = false) String code,@RequestParam(value = "encryptedData", required = false) String encryptedData,
                            @RequestParam(value = "iv",required = false) String iv) {
        System.out.println("*****************************");
        System.out.println(code);
        System.out.println(encryptedData);
        System.out.println(iv);
        //  Long supplierId= RequestHeaderUtil.getHeaderToLong(request,HeaderConstant.SUPPLIER_ID);
        //JSONObject jsonObject = JSONObject.fromObject(userVo.getCode());
        WxConfVo wxConfig = wxConfService.getWxConfEntity();
        AllUserEntity allUser = null;
        Map<String, Object> result = new HashMap<>();
        try {
            final WxMaService wxService = WxMaConfiguration.getMaService(wxConfig.getAppid(), wxConfig.getSecret());
            try {
                WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
                log.info(session.getOpenid());
                String wxOpenid = session.getOpenid();
                WxMaUserInfo wxMaUserInfo = wxService.getUserService().getUserInfo(session.getSessionKey(),encryptedData,iv);
                allUser = this.allUserService.readByOpenid(wxOpenid);
                //如果用户不存在，则注册用户
                if (allUser == null) {
                    allUser = new AllUserEntity();
                    allUser.setNickname(wxMaUserInfo.getNickName());
                    allUser.setAvatar(wxMaUserInfo.getAvatarUrl());
                    allUser.setAddress(wxMaUserInfo.getProvince()+"-"+wxMaUserInfo.getCity()+"-"+wxMaUserInfo.getCountry());
                    //TODO 获取用户的头像和昵称信息
                    allUser.setOpenid(wxOpenid);
                /*    allUser.setIntegral(5);
                    allUser.setRemainingPoints(5);*/
                    allUser = this.allUserService.register(allUser);
                    //积分记录
                   // integralRecordService.sendInteger(allUser.getId());

                }
                allUser.setLoginTime(new Date());
                allUser.setNickname(wxMaUserInfo.getNickName());
                allUser.setAvatar(wxMaUserInfo.getAvatarUrl());
                this.allUserService.update(allUser);
            } catch (WxErrorException e) {
                this.logger.error(e.getMessage(), e);

                throw new RequestEmptyException("登录失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //登录并创建token
        String token = authService.login1(allUser.getOpenid(), null);
        /**
         * 用户登录成功
         */
        if (token != null) {
            //查询用户信息

            ThreadUtil.execute(() -> {
                //TODO 更新用户的登录信息
            });
            result.put("allUser", allUser);
            result.put("token", token);
        } else {
            throw new RequestEmptyException("登录失败！");
        }
        return new SuccessResponseData(result);
    }

    /**
     * 解密手机号
     */
    @GetMapping(value = "/login/phone")
    @ApiOperation("解密手机号")
    @ResponseBody
    public Object loginVali1(@RequestParam(value = "code", required = false) String code,@RequestParam(value = "encryptedData", required = false) String encryptedData,
                            @RequestParam(value = "iv",required = false) String iv) {

        WxConfVo wxConfig = wxConfService.getWxConfEntity();
        AllUserEntity allUser = null;
        Map<String, Object> result = new HashMap<>();
        try {
            final WxMaService wxService = WxMaConfiguration.getMaService(wxConfig.getAppid(), wxConfig.getSecret());
            try {
                WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
                log.info(session.getOpenid());
                String wxOpenid = session.getOpenid();
                String phone =  AesCbcUtil.decrypt(encryptedData,session.getSessionKey(),iv,"utf-8");
               // WxMaUserInfo wxMaUserInfo = wxService.getUserService().getUserInfo(session.getSessionKey(),encryptedData,iv);
                allUser = this.allUserService.readByOpenid(wxOpenid);
                allUser.setPhone(phone);
            } catch (WxErrorException e) {
                this.logger.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("allUser", allUser);
        return new SuccessResponseData(result);

    }

}