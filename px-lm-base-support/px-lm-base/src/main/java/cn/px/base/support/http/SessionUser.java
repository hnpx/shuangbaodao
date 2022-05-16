package cn.px.base.support.http;

import java.io.Serializable;

/**
 * 用户会话信息
 * @author PXHLT
 * @since 2019年7月22日 上午9:34:50
 */
@SuppressWarnings("serial")
public class SessionUser implements Serializable {
    private Long id;
    private String userName;
    private String userPhone;
    private Boolean rememberMe = false;

    public SessionUser(Long id, String userName, String userPhone, boolean rememberMe) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.rememberMe = rememberMe;
    }

    public Long getId() {
        return id;
    }

    public SessionUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SessionUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public SessionUser setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public SessionUser setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }
}
