package cn.px.base.exception;

import cn.px.base.support.http.HttpCode;

/**
 * @author PXHLT
 * @since 2019年4月4日 下午2:59:06
 */
@SuppressWarnings("serial")
public class LoginException extends BaseException {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Exception e) {
        super(message, e);
    }

    @Override
    protected HttpCode getCode() {
        return HttpCode.LOGIN_FAIL;
    }
}
