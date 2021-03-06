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
 * ???????????????
 *
 * @author fengshuonan
 * @Date 2017???1???10??? ??????8:25:24
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
     * ???????????????????????????
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/read/current")
    @ResponseBody
    @ApiOperation("????????????????????????")
    public Object currentInfo(ModelMap modelMap) {
        //????????????????????????
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //???????????????????????????????????????????????????????????????
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
     * ?????????????????????
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/show/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        return "/login.html";
    }

    /**
     * ???????????????
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:41 PM
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        //????????????????????????
        if (LoginContextHolder.getContext().hasLogin()) {
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();

            //???????????????????????????????????????????????????????????????
            if (userIndexInfo == null) {
                model.addAttribute("tips", "????????????????????????????????????");
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
     * ?????????????????????
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
     * ???????????????????????????
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("????????????")
    public ResponseData loginVali(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        String username = super.getPara("username");
        String password = super.getPara("password");

        if (ToolUtil.isOneEmpty(username, password)) {
            throw new RequestEmptyException("????????????????????????");
        }

        //?????????????????????????????????????????????????????????????????????
        if (ConstantsContext.getTenantOpen()) {
            String tenantCode = super.getPara("tenantCode");
            if (ToolUtil.isNotEmpty(tenantCode)) {

                //???spring???????????????service?????????????????????????????????????????????????????????????????????
                TenantInfoService tenantInfoService = null;
                try {
                    tenantInfoService = SpringContextHolder.getBean(TenantInfoService.class);
                } catch (Exception e) {
                    throw new ServiceException(500, "??????????????????service????????????????????????guns-tenant?????????");
                }

                //??????????????????
                TenantInfo tenantInfo = tenantInfoService.getByCode(tenantCode);
                if (tenantInfo != null) {
                    String dbName = tenantInfo.getDbName();

                    //????????????????????????????????????
                    TenantCodeHolder.put(tenantCode);
                    DataBaseNameHolder.put(dbName);
                    DataSourceContextHolder.setDataSourceType(dbName);
                } else {
                    throw new ServiceException(BizExceptionEnum.NO_TENANT_ERROR);
                }
            }
        }

        //???????????????????????????
        if (ConstantsContext.getKaptchaOpen()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        //???????????????token
        String token = authService.login(username, password);
        return new SuccessResponseData(token);
    }

    /**
     * ???????????????token???????????????????????????oauth?????????
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/sysTokenLogin")
    public String sysTokenLogin(@RequestParam(value = "token", required = false) String token, Model model) {
        if (ToolUtil.isNotEmpty(token)) {

            //???session?????????????????????????????????token??????
            LoginUser user = sessionManager.getSession(token);
            if (user == null) {
                return "/login.html";
            }

            //???????????????????????????
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //?????????????????????????????????
            Map<String, Object> userIndexInfo = userService.getUserIndexInfo();
            if (userIndexInfo == null) {
                model.addAttribute("tips", "???????????????????????????????????????");
                return "/login.html";
            } else {
                model.addAllAttributes(userIndexInfo);
            }

            //??????cookie
            authService.addLoginCookie(token);

            return "/index.html";
        } else {
            model.addAttribute("tips", "token??????????????????????????????");
            return "/login.html";
        }
    }

    /**
     * ????????????
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
     * ???????????????????????????
     */
    @GetMapping(value = "/login/wx")
    @ApiOperation("??????????????????")
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
                //???????????????????????????????????????
                if (allUser == null) {
                    allUser = new AllUserEntity();
                    allUser.setNickname(wxMaUserInfo.getNickName());
                    allUser.setAvatar(wxMaUserInfo.getAvatarUrl());
                    allUser.setAddress(wxMaUserInfo.getProvince()+"-"+wxMaUserInfo.getCity()+"-"+wxMaUserInfo.getCountry());
                    //TODO ????????????????????????????????????
                    allUser.setOpenid(wxOpenid);
                /*    allUser.setIntegral(5);
                    allUser.setRemainingPoints(5);*/
                    allUser = this.allUserService.register(allUser);
                    //????????????
                   // integralRecordService.sendInteger(allUser.getId());

                }
                allUser.setLoginTime(new Date());
                allUser.setNickname(wxMaUserInfo.getNickName());
                allUser.setAvatar(wxMaUserInfo.getAvatarUrl());
                this.allUserService.update(allUser);
            } catch (WxErrorException e) {
                this.logger.error(e.getMessage(), e);

                throw new RequestEmptyException("???????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //???????????????token
        String token = authService.login1(allUser.getOpenid(), null);
        /**
         * ??????????????????
         */
        if (token != null) {
            //??????????????????

            ThreadUtil.execute(() -> {
                //TODO ???????????????????????????
            });
            result.put("allUser", allUser);
            result.put("token", token);
        } else {
            throw new RequestEmptyException("???????????????");
        }
        return new SuccessResponseData(result);
    }

    /**
     * ???????????????
     */
    @GetMapping(value = "/login/phone")
    @ApiOperation("???????????????")
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